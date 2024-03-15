package com.vf.eventhubserver.persona;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.NullException;
import com.vf.eventhubserver.exception.PatchException;
import com.vf.eventhubserver.exception.RemoveException;
import com.vf.eventhubserver.exception.UpdateException;

@CrossOrigin
@RestController
@Validated
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * @return all the employees in the database.
     * @throws FinderException
     */
    @GetMapping
    public ResponseEntity<Set<EmployeeDTO>> getAllEmployees() throws FinderException {
        return ResponseEntity.ok(employeeService.findAll());
    }

    /**
     * @param id
     * @return the employee with the given id.
     * @throws FinderException
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id)
            throws FinderException {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    /**
     * @param employeeDTO
     * @return the employee that was created.
     * @throws NullException if the requestBody is null.
     * @throws CreateException if the employee could not be created.
     */
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO)
            throws NullException, CreateException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(employeeDTO));
    }

    /**
     * @param id
     * @param employeeDTO
     * @return the updated employee. The PUT method updates the whole record.
     * @throws FinderException if the employee with the given id is not found.
     * @throws UpdateException if the employee could not be updated.
     * @throws IllegalArgumentException if the employeeDTO is null.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,
            @RequestBody EmployeeDTO employeeDTO)
            throws FinderException, UpdateException, IllegalArgumentException {
        if (!Objects.equals(employeeDTO.id(), id))
            throw new IllegalArgumentException("Customer id and query id is not the same");
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    /**
     * @param id
     * @param employeeDTO
     * @return the updated employee. The PATCH method updates only the fields that are not null.
     * @throws FinderException if the employee with the given id is not found.
     * @throws PatchException if the employee could not be patched.
     * @throws IllegalArgumentException if the employeeDTO is null.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDTO> patchEmployee(@PathVariable Long id,
            @RequestBody Map<String, Object> employeePatch)
            throws FinderException, PatchException, IllegalArgumentException {
        if (employeePatch.isEmpty())
            throw new NullException("Employee patch is empty");
        return ResponseEntity.ok(employeeService.patchEmployee(id, employeePatch));
    }

    /**
     * @param id
     * @return the deleted employee.
     * @throws FinderException if the employee with the given id is not found.
     * @throws RemoveException if the employee could not be removed.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id)
            throws FinderException, RemoveException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
