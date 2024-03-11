package com.vf.eventhubserver.domain.service.mapper;

import java.util.List;
import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.eventhubserver.domain.dto.AddressDTO;
import com.vf.eventhubserver.domain.dto.EmployeeDTO;
import com.vf.eventhubserver.domain.dto.VenueDTO;
import com.vf.eventhubserver.domain.model.Address;
import com.vf.eventhubserver.domain.model.Employee;
import com.vf.eventhubserver.domain.model.Venue;

@Mapper(componentModel = "spring")
public interface VenueMapper {

    // Mappage des propriétés entre Venue et VenueDTO
    VenueDTO toDTO(Venue venue);

    Venue toEntity(VenueDTO venueDTO);

    @IterableMapping(elementTargetType = VenueDTO.class)
    List<VenueDTO> toDTOs(Iterable<Venue> venues);

    @IterableMapping(elementTargetType = Venue.class)
    List<Venue> toEntities(Iterable<VenueDTO> venueDTOs);

    //////////////////////////////
    // Méthodes de mappage pour address @Embedded
    //////////////////////////////

    // Utilisation de AddressMapper pour mapper Address
    AddressMapper ADDRESS_MAPPER = Mappers.getMapper(AddressMapper.class);

    // Mappage for Address and its DTO
    default AddressDTO toAddressDTO(Address address) {
        return ADDRESS_MAPPER.toDTO(address);
    }

    default Address toAddress(AddressDTO addressDTO) {
        return ADDRESS_MAPPER.toEntity(addressDTO);
    }

    // Mappage for Address with Venue and its DTO
    default AddressDTO toAddressDTO(Venue venue) {
        Address address = venue.getAddress();
        if (address == null)
            throw new IllegalArgumentException("Venue address cannot be null");
        return toAddressDTO(address);
    }

    default Address toAddress(VenueDTO venueDTO) {
        AddressDTO addressDTO = venueDTO.getAddress();
        if (addressDTO == null)
            throw new IllegalArgumentException("Venue address cannot be null");
        return toAddress(addressDTO);
    }

    //////////////////////////////
    // Méthodes de mappage pour employee @OneToMany
    //////////////////////////////

    // Utilisation de EmployeeMapper pour mapper Employee
    EmployeeMapper EMPLOYEE_MAPPER = Mappers.getMapper(EmployeeMapper.class);

    // Mappage for Employee and its DTO
    default EmployeeDTO toEmployeeDTO(Employee employee) {
        return EMPLOYEE_MAPPER.toDTO(employee);
    }

    default Employee toEmployee(EmployeeDTO employeeDTO) {
        return EMPLOYEE_MAPPER.toEntity(employeeDTO);
    }

    @IterableMapping(elementTargetType = EmployeeDTO.class)
    default Set<EmployeeDTO> toEmployeeDTOs(Iterable<Employee> employees) {
        return EMPLOYEE_MAPPER.toDTOs(employees);
    }

    @IterableMapping(elementTargetType = Employee.class)
    default Set<Employee> toEmployees(Iterable<EmployeeDTO> employeeDTOs) {
        return EMPLOYEE_MAPPER.toEntities(employeeDTOs);
    }

    // Mappage for Employee with Venue and its DTO
    @IterableMapping(elementTargetType = EmployeeDTO.class)
    default Set<EmployeeDTO> toEmployeeDTO(Venue venue) {
        Set<Employee> employees = venue.getEmployees();
        if (employees == null)
            throw new IllegalArgumentException("Venue employees cannot be null");
        return toEmployeeDTOs(employees);
    }

    default Set<Employee> toEmployee(VenueDTO venueDTO) {
        Set<EmployeeDTO> employeesDTO = venueDTO.getEmployees();
        if (employeesDTO == null)
            throw new IllegalArgumentException("Venue employees cannot be null");
        return toEmployees(employeesDTO);
    }

}
