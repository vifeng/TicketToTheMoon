package com.vf.eventhubserver.domain.service.mapper;

import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.EmployeeDTO;
import com.vf.eventhubserver.domain.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDTO toDTO(Employee employee);

    Employee toEntity(EmployeeDTO employeeDTO);

    @IterableMapping(elementTargetType = EmployeeDTO.class)
    Set<EmployeeDTO> toDTOs(Iterable<Employee> employees);

    @IterableMapping(elementTargetType = Employee.class)
    Set<Employee> toEntities(Iterable<EmployeeDTO> employeeDTOs);

}
