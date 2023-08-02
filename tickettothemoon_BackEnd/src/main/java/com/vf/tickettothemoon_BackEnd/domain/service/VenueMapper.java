package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.AddressDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.VenueDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Address;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;

@Mapper
public interface VenueMapper {
    VenueMapper INSTANCE = Mappers.getMapper(VenueMapper.class);

    // Mappage des propriétés entre Venue et VenueDTO
    VenueDTO toVenueDTO(Venue venue);

    Venue toVenue(VenueDTO venueDTO);

    @IterableMapping(elementTargetType = VenueDTO.class)
    List<VenueDTO> toVenueDTOs(Iterable<Venue> venues);

    //////////////////////////////
    // Méthodes de mappage pour address
    //////////////////////////////

    // Utilisation de AddressMapper pour mapper Address
    AddressMapper ADDRESS_MAPPER = Mappers.getMapper(AddressMapper.class);

    // Mappage for Address and its DTO
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

    // Mappage for Address with Venue and its DTO
    default AddressDTO toAddressDTO(Venue venue) {
        if (venue == null) {
            return null;
        }
        return toAddressDTO(venue.getAddress());
    }

    default Address toAddress(VenueDTO venueDTO) {
        if (venueDTO == null) {
            return null;
        }
        return toAddress(venueDTO.getAddress());
    }

}
