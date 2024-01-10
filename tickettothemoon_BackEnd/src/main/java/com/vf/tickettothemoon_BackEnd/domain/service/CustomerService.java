package com.vf.tickettothemoon_BackEnd.domain.service;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import com.vf.tickettothemoon_BackEnd.domain.dao.CustomerRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.CustomerDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Customer;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.CustomerMapper;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.DuplicateKeyException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.RemoveException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> findAll() throws FinderException {
        Iterable<Customer> customers = customerRepository.findAll();
        int size = ((Collection<Customer>) customers).size();
        if (size == 0) {
            throw new FinderException("No Customers in the database");
        }
        List<CustomerDTO> customerDTOs = customerMapper.toCustomerDTOs(customers);
        return customerDTOs;
    }

    public CustomerDTO findById(Long id) throws FinderException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new FinderException("Customer with id {" + id + "} not found"));
        return customerMapper.toCustomerDTO(customer);
    }

    public CustomerDTO findByEmail(String email) throws FinderException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new FinderException("Customer with email {" + email + "} not found"));
        return customerMapper.toCustomerDTO(customer);
    }

    public CustomerDTO findByPhone(String phone) throws FinderException {
        Customer customer = customerRepository.findByPhoneNumber(phone).orElseThrow(
                () -> new FinderException("Customer with phone {" + phone + "} not found"));
        return customerMapper.toCustomerDTO(customer);
    }

    public CustomerDTO findByUsername(String username) throws FinderException {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(
                () -> new FinderException("Customer with username {" + username + "} not found"));
        return customerMapper.toCustomerDTO(customer);
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
            Customer customer = customerMapper.toCustomer(customerDTO);
            Customer savedCustomer = customerRepository.save(customer);
            CustomerDTO savedCustomerDTO = customerMapper.toCustomerDTO(savedCustomer);
            return savedCustomerDTO;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Customer is not created : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CreateException("Customer is not created" + e.getMessage(), e);
        }
    }

    public boolean customerExists(String email, String phone) {
        return customerRepository.existsByEmailAndPhone(email, phone);
    }


    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO)
            throws IllegalArgumentException, CreateException {
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

            Customer savedCustomer = customerRepository.save(existingCustomer);
            return customerMapper.toCustomerDTO(savedCustomer);
        } catch (FinderException e) {
            throw new FinderException("Customer is not updated : " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Customer is not updated : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CreateException("Customer is not updated" + e.getMessage(), e);
        }
    }

    public CustomerDTO patchCustomer(Long id, Map<String, Object> customerPatch)
            throws IllegalArgumentException, CreateException, FinderException {
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(id);
            if (optionalCustomer.isPresent()) {
                customerPatch.forEach((key, value) -> {
                    Field field = ReflectionUtils.findField(Customer.class, key);
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, optionalCustomer.get(), value);
                });
                Customer savedPatchCustomer = customerRepository.save(optionalCustomer.get());
                return customerMapper.toCustomerDTO(savedPatchCustomer);
            } else {
                throw new FinderException("Customer with id {" + customerPatch + "} not found");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Customer is not patched : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CreateException("Customer is not patched" + e.getMessage(), e);
        }
    }

    public void deleteCustomer(Long id) throws FinderException, RemoveException {
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
