package com.vf.eventhubserver.venue;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.eventhubserver.persona.Employee;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    public Venue findByName(String name);

    public Set<Employee> findAllEmployeesById(Long id);

    public Employee findEmployeeById(Long id);

}
