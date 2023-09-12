package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.util.Set;
import jakarta.validation.constraints.NotNull;

/**
 * A simple pojo for DTO purposes
 */
public record VenueDTO(Long id, @NotNull(message = "invalid Name") String name, AddressDTO address,
                Set<EmployeeDTO> employees) {


        public AddressDTO getAddress() {
                return address;
        }

        public AddressDTO setAddress(AddressDTO address) {
                return address;
        }

        public Set<EmployeeDTO> getEmployees() {
                return employees;
        }

        public Set<EmployeeDTO> setEmployees(Set<EmployeeDTO> employees) {
                return employees;
        }
}
