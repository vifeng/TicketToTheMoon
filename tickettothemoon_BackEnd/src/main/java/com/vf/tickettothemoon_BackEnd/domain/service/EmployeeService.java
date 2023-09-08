package com.vf.tickettothemoon_BackEnd.domain.service;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import com.vf.tickettothemoon_BackEnd.domain.dao.EmployeeRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.EmployeeDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.EmployeeMapper;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.PatchException;
import com.vf.tickettothemoon_BackEnd.exception.RemoveException;
import com.vf.tickettothemoon_BackEnd.exception.UpdateException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeeService {
    private EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;

    }

    /**
     * @return all the employees DTO in the database.
     */
    public List<EmployeeDTO> findAll() throws FinderException {
        Iterable<Employee> employees = employeeRepository.findAll();
        int size = ((Collection<Employee>) employees).size();
        if (size == 0) {
            throw new FinderException("No Employees in the database");
        }
        List<EmployeeDTO> employeeDTOs = EmployeeMapper.INSTANCE.toEmployeeDTOs(employees);
        return employeeDTOs;

    }

    /**
     * @param id
     * @return the employee DTO with the given id.
     */
    public EmployeeDTO findById(Long id) throws FinderException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new FinderException("Employee with id {" + id + "} not found"));
        return EmployeeMapper.INSTANCE.toEmployeeDTO(employee);
    }

    /**
     * @param employeeDTO
     * @return the employee DTO.
     * @throws CreateException
     * @throws IllegalArgumentException
     */
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO)
            throws CreateException, IllegalArgumentException {
        try {
            Employee employee = EmployeeMapper.INSTANCE.toEmployee(employeeDTO);
            Employee savedEmployee = employeeRepository.save(employee);
            EmployeeDTO savedEmployeeDTO = EmployeeMapper.INSTANCE.toEmployeeDTO(savedEmployee);
            return savedEmployeeDTO;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee is not created : " + e);
        } catch (Exception e) {
            throw new CreateException("Employee is not created");
        }
    }

    /**
     * 
     * @param id
     * @param employeeDTO
     * @return the whole updated employee DTO.
     * @throws FinderException
     * @throws UpdateException
     * @throws IllegalArgumentException
     */
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO)
            throws FinderException, UpdateException, IllegalArgumentException {
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (optionalEmployee.isPresent()) {
                Employee existingEmployee = optionalEmployee.get();
                // Check null values and required fields
                if (employeeDTO.username() != null)
                    existingEmployee.setUsername(employeeDTO.username());
                if (employeeDTO.password() != null)
                    existingEmployee.setPassword(employeeDTO.password());
                if (employeeDTO.email() != null)
                    existingEmployee.setEmail(employeeDTO.email());
                Employee updatedEmployee = employeeRepository.save(existingEmployee);
                return EmployeeMapper.INSTANCE.toEmployeeDTO(updatedEmployee);
            } else {
                throw new FinderException("Employee with id {" + id + "} not found");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Employee with id {" + id + "} update failed : " + e);
        } catch (Exception e) {
            throw new UpdateException("Employee with id {" + id + "} update failed" + e);
        }

    }


    /**
     * @param id
     * @param Map<String, Object> employeePatch
     * @return a partial update of the employee DTO. very interesting for nested or huge objects.
     * @throws FinderException
     * @throws PatchException
     * @throws IllegalArgumentException
     */
    public EmployeeDTO patchEmployee(Long id, Map<String, Object> employeePatch)
            throws FinderException, PatchException, IllegalArgumentException {
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (optionalEmployee.isPresent()) {
                employeePatch.forEach((key, value) -> {
                    Field field = ReflectionUtils.findField(Employee.class, key);
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, optionalEmployee.get(), value);
                });
                Employee updatedEmployee = employeeRepository.save(optionalEmployee.get());
                return EmployeeMapper.INSTANCE.toEmployeeDTO(updatedEmployee);
            } else {
                throw new FinderException("Employee with id {" + id + "} not found");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee with id {" + id + "} patch failed : " + e);
        } catch (Exception e) {
            throw new PatchException("Employee with id {" + id + "} patch failed" + e);
        }
    }

    /**
     * @param id
     * @return the deleted employee DTO.
     * @throws FinderException
     * @throws RemoveException
     */
    public EmployeeDTO deleteEmployee(Long id) throws FinderException, RemoveException {
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (optionalEmployee.isPresent()) {
                Employee existingEmployee = optionalEmployee.get();
                employeeRepository.delete(existingEmployee);
                return EmployeeMapper.INSTANCE.toEmployeeDTO(existingEmployee);
            } else {
                throw new FinderException("Employee with id {" + id + "} not found");
            }
        } catch (Exception e) {
            throw new RemoveException("Employee with id {" + id + "} delete failed" + e);
        }
    }


}
