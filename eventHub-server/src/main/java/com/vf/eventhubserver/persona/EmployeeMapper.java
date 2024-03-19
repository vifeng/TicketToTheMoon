package com.vf.eventhubserver.persona;

import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO toDTO(Employee employee);

    Employee toEntity(EmployeeDTO employeeDTO);

    @IterableMapping(elementTargetType = EmployeeDTO.class)
    Set<EmployeeDTO> toDTOs(Iterable<Employee> employees);

    @IterableMapping(elementTargetType = Employee.class)
    Set<Employee> toEntities(Iterable<EmployeeDTO> employeeDTOs);

    EmployeeDTONoPwd toDTONoPwd(Employee employee);

    Employee toEntityNoPwd(EmployeeDTONoPwd employeeDTONoPwd);

}
