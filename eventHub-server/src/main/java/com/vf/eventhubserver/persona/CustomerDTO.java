package com.vf.eventhubserver.persona;

public record CustomerDTO(
    Long id,
    String firstName,
    String lastName,
    String username,
    String email,
    String phoneNumber,
    AddressDTO address,
    String creditCardNumber) {}
