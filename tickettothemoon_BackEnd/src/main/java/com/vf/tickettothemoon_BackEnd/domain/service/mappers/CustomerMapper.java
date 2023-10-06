package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vf.tickettothemoon_BackEnd.domain.dto.AddressDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.CustomerDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Address;
import com.vf.tickettothemoon_BackEnd.domain.model.Customer;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    // Mappage des propriétés entre Customer et CustomerDTO
    CustomerDTO toCustomerDTO(Customer customer);

    Customer toCustomer(CustomerDTO customerDTO);

    @IterableMapping(elementTargetType = CustomerDTO.class)
    List<CustomerDTO> toCustomerDTOs(Iterable<Customer> customers);

    @IterableMapping(elementTargetType = Customer.class)
    List<Customer> toCustomers(Iterable<CustomerDTO> customerDTOs);

    //////////////////////////////
    // Méthodes de mappage pour address
    //////////////////////////////
    // Utilisation de AddressMapper pour mapper Address
    AddressMapper ADDRESS_MAPPER = Mappers.getMapper(AddressMapper.class);

    // Mappage for Address with ADDRESS and its DTO
    default AddressDTO toAddressDTO(Address address) {
        if (address == null) {
            return null;
        }
        return ADDRESS_MAPPER.toAddressDTO(address);
    }

    default Address toAddress(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }
        return ADDRESS_MAPPER.toAddress(addressDTO);
    }

    // Mappage for Address with CUSTOMER and its DTO
    default AddressDTO toAddressDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        return toAddressDTO(customer.getAddress());
    }

    default Address toAddress(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }
        return toAddress(customerDTO.getAddress());
    }

}
