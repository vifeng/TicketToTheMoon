package com.vf.eventhubserver.venue;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.vf.eventhubserver.persona.Address;
import com.vf.eventhubserver.persona.Employee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Venue")
public class Venue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    @NotNull(message = "invalid Name")
    private String name;

    @Embedded
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "venue_FK")
    Set<Employee> employees = new HashSet<>();

    public Venue() {}

    public Venue(Long id, String name, Address address, Set<Employee> employees) {
        setId(id);
        setName(name);
        setAddress(address);
        setEmployees(employees);
    }

    public Venue(String name, Address address) {
        setName(name);
        setAddress(address);
    }

    public Venue(String name, Address address, Set<Employee> employees) {
        setName(name);
        setAddress(address);
        setEmployees(employees);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        if (this.employees != null)
            this.employees.remove(employee);
    }

    public void removeAllEmployees() {
        if (this.employees != null)
            this.employees.clear();
    }

    @Override
    public String toString() {
        return "Venue [id=" + id + ", name=" + name + ", address=" + address + ", employees="
                + employees + "]";
    }

    public void add(Employee employee) {}

}
