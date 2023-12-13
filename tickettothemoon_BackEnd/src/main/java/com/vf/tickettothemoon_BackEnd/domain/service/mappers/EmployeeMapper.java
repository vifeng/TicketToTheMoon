package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vf.tickettothemoon_BackEnd.domain.dto.EmployeeDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO toEmployeeDTO(Employee employee);

    @Mapping(target = "id", ignore = true)
    Employee toEmployee(EmployeeDTO employeeDTO);

    @IterableMapping(elementTargetType = EmployeeDTO.class)
    Set<EmployeeDTO> toEmployeeDTOs(Iterable<Employee> employees);

    @IterableMapping(elementTargetType = Employee.class)
    Set<Employee> toEmployees(Iterable<EmployeeDTO> employeeDTOs);

}
