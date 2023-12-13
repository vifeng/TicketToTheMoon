package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.AddressDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDTO toAddressDTO(Address address);

    Address toAddress(AddressDTO addressDTO);

    @IterableMapping(elementTargetType = AddressDTO.class)
    Iterable<AddressDTO> toAddressDTOs(Iterable<Address> addresses);

    @IterableMapping(elementTargetType = Address.class)
    Iterable<Address> toAddresses(Iterable<AddressDTO> addressDTOs);
}
