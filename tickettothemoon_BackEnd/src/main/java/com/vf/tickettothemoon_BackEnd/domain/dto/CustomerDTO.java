package com.vf.tickettothemoon_BackEnd.domain.dto;

public record CustomerDTO(Long id, String firstName, String username, String lastName, String email,
                String phoneNumber, AddressDTO address, int creditCardNumber) {
        public AddressDTO getAddress() {
                return address;
        }
}
