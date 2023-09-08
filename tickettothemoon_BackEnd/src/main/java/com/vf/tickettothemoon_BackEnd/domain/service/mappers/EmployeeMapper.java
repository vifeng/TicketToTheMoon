package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.EmployeeDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO toEmployeeDTO(Employee employee);

    @Mapping(target = "id", ignore = true)
    Employee toEmployee(EmployeeDTO employeeDTO);

    @IterableMapping(elementTargetType = EmployeeDTO.class)
    List<EmployeeDTO> toEmployeeDTOs(Iterable<Employee> employees);

    @IterableMapping(elementTargetType = Employee.class)
    List<Employee> toEmployees(Iterable<EmployeeDTO> employeeDTOs);

}
