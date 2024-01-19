package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.tickettothemoon_BackEnd.domain.dao.CustomerRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.CustomerDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Customer;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.CustomerMapper;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.DuplicateKeyException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.NullException;
import com.vf.tickettothemoon_BackEnd.exception.RemoveException;
import com.vf.tickettothemoon_BackEnd.exception.UpdateException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private ObjectMapper objectMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper,
            ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.objectMapper = objectMapper;
    }

    public List<CustomerDTO> findAll() throws FinderException {
        Iterable<Customer> customers = customerRepository.findAll();
        int size = ((Collection<Customer>) customers).size();
        if (size == 0) {
            throw new FinderException("No Customers in the database");
        }
        List<CustomerDTO> customerDTOs = customerMapper.toDTOs(customers);
        return customerDTOs;
    }

    public CustomerDTO findById(@NonNull Long id) throws FinderException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new FinderException("Customer with id {" + id + "} not found"));
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO findByEmail(String email) throws FinderException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new FinderException("Customer with email {" + email + "} not found"));
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO findByPhone(String phone) throws FinderException {
        Customer customer = customerRepository.findByPhoneNumber(phone).orElseThrow(
                () -> new FinderException("Customer with phone {" + phone + "} not found"));
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO findByUsername(String username) throws FinderException {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new FinderException("Customer with username {" + username + "} not found"));
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO)
            throws IllegalArgumentException, CreateException {
        try {
            if (customerDTO.id() != null) {
                throw new IllegalArgumentException("Customer id must be null");
            }
            if (customerExists(customerDTO.email(), customerDTO.phoneNumber())) {
                throw new DuplicateKeyException("Customer with email" + customerDTO.email()
                        + " and phone " + customerDTO.phoneNumber() + " already exists");
            }
            Customer customer = customerMapper.toEntity(customerDTO);
            Customer savedCustomer = customerRepository.save(customer);
            CustomerDTO savedCustomerDTO = customerMapper.toDTO(savedCustomer);
            return savedCustomerDTO;
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
            if (id == null) {
                throw new NullException("Customer id cannot be null");
            } else {
                Optional<Customer> optionalCustomer = customerRepository.findById(id);
                if (!optionalCustomer.isPresent()) {
                    throw new FinderException("Customer with id {" + id + "} not found");
                }
                Customer updatedCustomer = customerMapper.toEntity(customerUpdate);
                Customer savedCustomer = customerRepository.save(updatedCustomer);
                return customerMapper.toDTO(savedCustomer);
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Customer with id {" + id + "} update failed" + e.getMessage(), e);
        } catch (Exception e) {
            throw new UpdateException(
                    "Customer with id {" + id + "} update failed" + e.getMessage(), e);
        }
    }

    /////////////////////////
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO)
            throws IllegalArgumentException, CreateException, FinderException {
        try {
            Optional<Customer> customerOptional = customerRepository.findById(id);
            if (!customerOptional.isPresent()) {
                throw new FinderException("Customer with id {" + customerDTO.id() + "} not found");
            }

            Customer existingCustomer = customerOptional.get();
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
            throw new FinderException("Customer with id {" + id + "} not found");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Customer with id {" + id + "} not found");
        } catch (Exception e) {
            throw new RemoveException("Customer with id {" + id + "} not removed");
        }
    }
}
