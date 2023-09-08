package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.util.Set;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;
import jakarta.validation.constraints.NotNull;

/**
 * A simple pojo for DTO purposes
 */
public record VenueDTO(Long id, @NotNull(message = "invalid Name") String name, AddressDTO address,
                Set<Employee> employees) {


        public AddressDTO getAddress() {
                return address;
        }

}
