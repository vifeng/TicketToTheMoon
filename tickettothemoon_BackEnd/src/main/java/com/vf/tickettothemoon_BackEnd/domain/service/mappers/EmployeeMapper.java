package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.EmployeeDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO toDTO(Employee employee);

    Employee toEntity(EmployeeDTO employeeDTO);

    @IterableMapping(elementTargetType = EmployeeDTO.class)
    Set<EmployeeDTO> toDTOs(Iterable<Employee> employees);

    @IterableMapping(elementTargetType = Employee.class)
    Set<Employee> toEntities(Iterable<EmployeeDTO> employeeDTOs);

}
