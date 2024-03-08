package com.vf.eventhubserver.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.eventhubserver.domain.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
