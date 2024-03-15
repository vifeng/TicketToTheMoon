package com.vf.eventhubserver.persona;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.DuplicateKeyException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.NullException;
import com.vf.eventhubserver.exception.RemoveException;
import com.vf.eventhubserver.exception.UpdateException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    static final String NOTFOUNDMSG = "} not found";
    static final String CUSTMSG = "Customer with id {";
    static final String CUSTFMSG = "Customer with {";
    static final String CUNULLMSG = "Customer is null";

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> findAll() throws FinderException {
        Iterable<Customer> customers = customerRepository.findAll();
        int size = ((Collection<Customer>) customers).size();
        if (size == 0) {
            throw new NullException("No Customers in the database");
        }
        return customerMapper.toDTOs(customers);
    }

    public CustomerDTO findById(Long id) throws FinderException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new FinderException(CUSTMSG + id + NOTFOUNDMSG));
        if (customer == null) {
            throw new NullException(CUSTMSG + id + CUNULLMSG);
        }
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO findByEmail(String email) throws FinderException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new FinderException("Customer with email {" + email + NOTFOUNDMSG));
        if (customer == null) {
            throw new NullException(CUSTFMSG + email + CUNULLMSG);
        }
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO findByPhone(String phone) throws FinderException {
        Customer customer = customerRepository.findByPhoneNumber(phone).orElseThrow(
                () -> new FinderException("Customer with phone {" + phone + NOTFOUNDMSG));
        if (customer == null) {
            throw new NullException(CUSTFMSG + phone + CUNULLMSG);
        }
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO findByUsername(String username) throws FinderException {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new FinderException("Customer with username {" + username + NOTFOUNDMSG));
        if (customer == null) {
            throw new NullException(CUSTFMSG + username + CUNULLMSG);
        }
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO)
            throws IllegalArgumentException, CreateException {
        try {
            if (customerDTO.id() != null) {
                throw new IllegalArgumentException("Customer id must be null");
            }
            String email = customerDTO.email();
            String phoneNumber = customerDTO.phoneNumber();
            if (email == null || phoneNumber == null) {
                throw new IllegalArgumentException("Customer email and phone number must be set");
            }
            if (customerExists(email, phoneNumber)) {
                throw new DuplicateKeyException("Customer with email" + email + " and phone "
                        + phoneNumber + " already exists");
            }
            Customer customer = customerMapper.toEntity(customerDTO);
            Customer savedCustomer = customerRepository.save(customer);
            return customerMapper.toDTO(savedCustomer);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Customer is not created : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CreateException("Customer is not created" + e.getMessage(), e);
        }
    }

    public boolean customerExists(String email, String phoneNumber) {
        return customerRepository.existsByEmailAndPhoneNumber(email, phoneNumber);
    }

    /**
     * Update -> replace the whole object : updates only the given fields and erase the rest of the
     * existing fields.
     * 
     * @param id
     * @param customerUpdate
     * @return
     * @throws IllegalArgumentException
     * @throws CreateException
     */
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerUpdate)
            throws IllegalArgumentException, UpdateException, FinderException {
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(id);
            if (!optionalCustomer.isPresent()) {
                throw new FinderException(CUSTMSG + id + NOTFOUNDMSG);
            }
            Customer updatedCustomer = customerMapper.toEntity(customerUpdate);
            Customer savedCustomer = customerRepository.save(updatedCustomer);
            return customerMapper.toDTO(savedCustomer);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(CUSTMSG + id + "} update failed" + e.getMessage(),
                    e);
        } catch (Exception e) {
            throw new UpdateException(CUSTMSG + id + "} update failed" + e.getMessage(), e);
        }
    }

    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO)
            throws IllegalArgumentException, CreateException, FinderException {
        try {
            Optional<Customer> customerOptional = customerRepository.findById(id);
            if (!customerOptional.isPresent()) {
                throw new FinderException(CUSTMSG + customerDTO.id() + NOTFOUNDMSG);
            }
            Customer existingCustomer = customerOptional.get();
            if (existingCustomer == null) {
                throw new IllegalArgumentException(CUNULLMSG);
            }
            if (customerDTO.firstName() != null)
                existingCustomer.setFirstName(customerDTO.firstName());
            if (customerDTO.lastName() != null)
                existingCustomer.setLastName(customerDTO.lastName());
            if (customerDTO.username() != null)
                existingCustomer.setUsername(customerDTO.username());
            if (customerDTO.email() != null)
                existingCustomer.setEmail(customerDTO.email());
            if (customerDTO.phoneNumber() != null)
                existingCustomer.setPhoneNumber(customerDTO.phoneNumber());
            if (customerDTO.address() != null) {
                if (customerDTO.address().street() != null)
                    existingCustomer.getAddress().setStreet(customerDTO.address().street());
                if (customerDTO.address().city() != null)
                    existingCustomer.getAddress().setCity(customerDTO.address().city());
                if (customerDTO.address().zipcode() != null)
                    existingCustomer.getAddress().setZipcode(customerDTO.address().zipcode());
                if (customerDTO.address().country() != null)
                    existingCustomer.getAddress().setCountry(customerDTO.address().country());
            }
            if (customerDTO.creditCardNumber() != null)
                existingCustomer.setCreditCardNumber(customerDTO.creditCardNumber());

            Customer savedCustomer = customerRepository.save(existingCustomer);
            return customerMapper.toDTO(savedCustomer);
        } catch (FinderException e) {
            throw new FinderException("Customer is not updated : " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Customer is not updated : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CreateException("Customer is not updated" + e.getMessage(), e);
        }
    }

    public void deleteCustomer(Long id)
            throws FinderException, IllegalArgumentException, RemoveException {

        try {
            customerRepository.getReferenceById(id);
            customerRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new FinderException(CUSTMSG + id + NOTFOUNDMSG);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(CUSTMSG + id + NOTFOUNDMSG);
        } catch (Exception e) {
            throw new RemoveException(CUSTMSG + id + "} not removed");
        }
    }
}
