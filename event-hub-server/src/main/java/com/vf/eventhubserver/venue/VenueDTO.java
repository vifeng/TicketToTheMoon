package com.vf.eventhubserver.venue;

import com.vf.eventhubserver.persona.AddressDTO;
import com.vf.eventhubserver.persona.EmployeeDTO;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record VenueDTO(
    Long id,
    @NotNull(message = "invalid Name") String name,
    AddressDTO address,
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
