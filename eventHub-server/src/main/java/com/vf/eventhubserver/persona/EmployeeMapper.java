package com.vf.eventhubserver.persona;

import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  EmployeeDTO toDTO(Employee employee);

  Employee toEntity(EmployeeDTO employeeDTO);

  @IterableMapping(elementTargetType = EmployeeDTO.class)
  Set<EmployeeDTO> toDTOs(Iterable<Employee> employees);

  @IterableMapping(elementTargetType = Employee.class)
  Set<Employee> toEntities(Iterable<EmployeeDTO> employeeDTOs);

  @Mapping(target = "employee.password", ignore = true)
  EmployeeDTONoPwd toDTONoPwd(Employee employee);

  @Mapping(target = "password", ignore = true)
  Employee toEntityNoPwd(EmployeeDTONoPwd employeeDTONoPwd);
}
