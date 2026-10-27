package com.example.employee.repository;

import java.util.List;
import java.util.Optional;

import com.example.employee.model.Employee;

public interface EmployeeRepository {

    Employee save(Employee employee);

    List<Employee> findAll();

    Optional<Employee> findByEmail(String email);
}
