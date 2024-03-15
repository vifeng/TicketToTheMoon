package com.vf.eventhubserver.persona;

import java.util.List;
import java.util.Objects;
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
import com.vf.eventhubserver.exception.RemoveException;
import com.vf.eventhubserver.exception.UpdateException;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() throws FinderException {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id)
            throws FinderException {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@PathVariable String email)
            throws FinderException {
        return ResponseEntity.ok(customerService.findByEmail(email));
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public ResponseEntity<CustomerDTO> getCustomerByPhone(@PathVariable String phoneNumber)
            throws FinderException {
        return ResponseEntity.ok(customerService.findByPhone(phoneNumber));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<CustomerDTO> getCustomerByUsername(@PathVariable String username)
            throws FinderException {
        return ResponseEntity.ok(customerService.findByUsername(username));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO)
            throws NullException, CreateException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(customerService.createCustomer(customerDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id,
            @RequestBody CustomerDTO customerUpdateDTO)
            throws NullException, FinderException, UpdateException, IllegalArgumentException {
        if (!Objects.equals(customerUpdateDTO.id(), id)) {
            throw new IllegalArgumentException("Customer id and query id is not the same");
        }
        return ResponseEntity.ok(customerService.updateCustomer(id, customerUpdateDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable Long id,
            @RequestBody CustomerDTO customerPatch)
            throws NullException, FinderException, UpdateException, IllegalArgumentException {
        return ResponseEntity.ok(customerService.patchCustomer(id, customerPatch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id)
            throws FinderException, RemoveException {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

}
