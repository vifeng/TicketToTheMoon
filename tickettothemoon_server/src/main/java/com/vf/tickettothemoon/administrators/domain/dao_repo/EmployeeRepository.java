package com.vf.tickettothemoon.administrators.domain.dao_repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vf.tickettothemoon.administrators.domain.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    
}
