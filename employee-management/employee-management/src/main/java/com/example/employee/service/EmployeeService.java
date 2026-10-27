package com.example.employee.service;

import java.util.List;

import com.example.employee.model.Employee;

public interface EmployeeService {

    /** Registers a new employee. Throws DuplicateEmployeeException if the email is already used. */
    Employee registerEmployee(Employee employee);

    List<Employee> getAllEmployees();
}
