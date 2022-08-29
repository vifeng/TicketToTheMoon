package com.vf.tickettothemoon.administrators.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vf.tickettothemoon.administrators.domain.dao_repo.EmployeeRepository;

import java.util.List;
import com.vf.tickettothemoon.administrators.domain.model.Employee;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000/")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> fetchEmployee(){
        return employeeRepository.findAll();
    }
    
}
