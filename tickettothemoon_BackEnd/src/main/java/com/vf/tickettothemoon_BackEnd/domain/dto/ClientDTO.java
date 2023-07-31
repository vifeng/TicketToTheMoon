package com.vf.tickettothemoon_BackEnd.domain.dto;

import org.springframework.data.annotation.Id;

public record ClientDTO(@Id Long id, String firstName, String username, String lastName,
                String email, String phoneNumber, AddressDTO address, int creditCardNumber) {
        public AddressDTO getAddress() {
                return address;
        }
}
