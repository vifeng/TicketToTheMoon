package com.vf.eventhubserver.persona;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  CustomerDTO toDTO(Customer customer);

  Customer toEntity(CustomerDTO customerDTO);

  @IterableMapping(elementTargetType = CustomerDTO.class)
  List<CustomerDTO> toDTOs(Iterable<Customer> customers);

  @IterableMapping(elementTargetType = Customer.class)
  List<Customer> toEntities(Iterable<CustomerDTO> customerDTOs);
}
