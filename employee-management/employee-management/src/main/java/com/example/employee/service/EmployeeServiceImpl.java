package com.example.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employee.exception.DuplicateEmployeeException;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;

/**
 * Business logic only. Notice there is NO logging code here:
 * auditing is handled separately by AuditLoggingAspect (Spring AOP).
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee registerEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee must not be null");
        }
        if (repository.findByEmail(employee.getEmail()).isPresent()) {
            throw new DuplicateEmployeeException(
                    "An employee with email '" + employee.getEmail() + "' already exists");
        }
        return repository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }
}
