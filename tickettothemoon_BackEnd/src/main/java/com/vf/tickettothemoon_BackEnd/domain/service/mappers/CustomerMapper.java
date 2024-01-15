package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.CustomerDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toCustomerDTO(Customer customer);

    Customer toCustomer(CustomerDTO customerDTO);

    @IterableMapping(elementTargetType = CustomerDTO.class)
    List<CustomerDTO> toCustomerDTOs(Iterable<Customer> customers);

    @IterableMapping(elementTargetType = Customer.class)
    List<Customer> toCustomers(Iterable<CustomerDTO> customerDTOs);

}
