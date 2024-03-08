package com.vf.eventhubserver.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.CustomerDTO;
import com.vf.eventhubserver.domain.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toDTO(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);

    @IterableMapping(elementTargetType = CustomerDTO.class)
    List<CustomerDTO> toDTOs(Iterable<Customer> customers);

    @IterableMapping(elementTargetType = Customer.class)
    List<Customer> toEntities(Iterable<CustomerDTO> customerDTOs);

}
