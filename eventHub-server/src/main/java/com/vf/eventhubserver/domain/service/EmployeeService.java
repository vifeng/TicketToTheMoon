package com.vf.eventhubserver.domain.service;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.vf.eventhubserver.domain.dao.EmployeeRepository;
import com.vf.eventhubserver.domain.dto.EmployeeDTO;
import com.vf.eventhubserver.domain.model.Employee;
import com.vf.eventhubserver.domain.service.mapper.EmployeeMapper;
import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.NullException;
import com.vf.eventhubserver.exception.PatchException;
import com.vf.eventhubserver.exception.RemoveException;
import com.vf.eventhubserver.exception.UpdateException;

@Service
@Transactional
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;
    static final String EMPMSG = "Employee with id {";
    static final String NOTFOUNDMSG = "} not found";
    static final String EMPNULL = "Employee cannot be null";

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * @return all the employees DTO in the database.
     */
    public Set<EmployeeDTO> findAll() throws FinderException {
        Iterable<Employee> employees = employeeRepository.findAll();
        int size = ((Collection<Employee>) employees).size();
        if (size == 0) {
            throw new FinderException("No Employees in the database");
        }
        return employeeMapper.toDTOs(employees);

    }

    /**
     * @param id
     * @return the employee DTO with the given id.
     */
    public EmployeeDTO findById(Long id) throws FinderException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new FinderException(EMPMSG + id + NOTFOUNDMSG));
        if (employee == null) {
            throw new NullException(EMPNULL);
        }
        return employeeMapper.toDTO(employee);
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
            Employee employee = employeeMapper.toEntity(employeeDTO);
            Employee savedEmployee = employeeRepository.save(employee);
            return employeeMapper.toDTO(savedEmployee);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Employee is not created : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new CreateException("Employee is not created : " + e.getMessage(), e);
        }
    }

    /**
     * Send the complete object. It replaces the old one.
     * 
     * @param id
     * @param employeeDTO
     * @return the updated employee DTO.
     * @throws FinderException
     * @throws UpdateException
     * @throws IllegalArgumentException
     */
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTOUpdate)
            throws FinderException, UpdateException, IllegalArgumentException {
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (!optionalEmployee.isPresent()) {
                throw new FinderException(EMPMSG + id + NOTFOUNDMSG);
            }
            Employee updatedEmployee = employeeMapper.toEntity(employeeDTOUpdate);
            Employee savedEmployee = employeeRepository.save(updatedEmployee);
            return employeeMapper.toDTO(savedEmployee);
        } catch (FinderException e) {
            throw new FinderException(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(EMPMSG + id + "} update failed : " + e.getMessage(),
                    e);
        } catch (Exception e) {
            throw new UpdateException(EMPMSG + id + "} update failed : " + e.getMessage(), e);
        }

    }

    /**
     * Partial update. Sends only the fields to update. the others are not modified.
     * 
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
                    if (key != null) {
                        Field field = ReflectionUtils.findField(Employee.class, key);
                        if (field != null) {
                            ReflectionUtils.makeAccessible(field);
                            if (value != null)
                                ReflectionUtils.setField(field, optionalEmployee.get(), value);
                        }
                    }
                });
                @SuppressWarnings("null")
                Employee patchEmployee = employeeRepository.save(optionalEmployee.get());
                return employeeMapper.toDTO(patchEmployee);
            } else {
                throw new FinderException(EMPMSG + id + NOTFOUNDMSG);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(EMPMSG + id + "} patch failed : " + e.getMessage(),
                    e);
        } catch (Exception e) {
            throw new PatchException(EMPMSG + id + "} patch failed" + e.getMessage(), e);
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
                Employee employeeToDelete = optionalEmployee.get();
                if (employeeToDelete == null) {
                    throw new NullException(EMPNULL);
                }
                employeeRepository.delete(employeeToDelete);
                return employeeMapper.toDTO(employeeToDelete);
            } else {
                throw new FinderException(EMPMSG + id + NOTFOUNDMSG);
            }
        } catch (Exception e) {
            throw new RemoveException(EMPMSG + id + "} delete failed" + e.getMessage(), e);
        }
    }

}
