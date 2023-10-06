package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vf.tickettothemoon_BackEnd.domain.dto.AddressDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Address;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO toAddressDTO(Address address);

    Address toAddress(AddressDTO addressDTO);

    @IterableMapping(elementTargetType = AddressDTO.class)
    Iterable<AddressDTO> toAddressDTOs(Iterable<Address> addresses);

    @IterableMapping(elementTargetType = Address.class)
    Iterable<Address> toAddresses(Iterable<AddressDTO> addressDTOs);
}
