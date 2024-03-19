package com.vf.eventhubserver.venue;

import com.vf.eventhubserver.persona.Employee;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {

  public Venue findByName(String name);

  public Set<Employee> findAllEmployeesById(Long id);

  public Employee findEmployeeById(Long id);
}
