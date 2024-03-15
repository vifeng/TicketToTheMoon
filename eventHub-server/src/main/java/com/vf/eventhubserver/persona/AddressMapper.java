package com.vf.eventhubserver.persona;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDTO toDTO(Address address);

    Address toEntity(AddressDTO addressDTO);

    @IterableMapping(elementTargetType = AddressDTO.class)
    Iterable<AddressDTO> toDTOs(Iterable<Address> addresses);

    @IterableMapping(elementTargetType = Address.class)
    Iterable<Address> toEntities(Iterable<AddressDTO> addressDTOs);
}
