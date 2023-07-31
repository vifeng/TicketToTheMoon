package com.vf.tickettothemoon_BackEnd.domain.dto;

import jakarta.validation.constraints.NotNull;

/**
 * A simple pojo for DTO purposes
 */
public record VenueDTO(Long id, @NotNull(message = "invalid Name") String name, String contact,
                String mail, AddressDTO address) {

        public AddressDTO getAddress() {
                return address;
        }

}
