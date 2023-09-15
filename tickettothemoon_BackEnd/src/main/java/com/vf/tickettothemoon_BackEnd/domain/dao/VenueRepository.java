package com.vf.tickettothemoon_BackEnd.domain.dao;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    public Venue findByName(String name);

    // find all employees by venue id
    public Set<Employee> findAllEmployeesById(Long id);

    public Employee findEmployeeById(Long id);

}
