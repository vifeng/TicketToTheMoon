package com.vf.tickettothemoon_BackEnd.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
