package com.example.employee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.employee.exception.DuplicateEmployeeException;
import com.example.employee.model.Employee;
import com.example.employee.repository.InMemoryEmployeeRepository;

class EmployeeServiceTest {

    private EmployeeService service;

    @BeforeEach
    void setUp() {
        service = new EmployeeServiceImpl(new InMemoryEmployeeRepository());
    }

    private Employee sample(String email) {
        return new Employee("Ravi Kumar", email, "9876543210", "Engineering",
                new BigDecimal("50000"), LocalDate.of(2024, 1, 15));
    }

    @Test
    void registerEmployee_assignsIdAndStoresEmployee() {
        Employee saved = service.registerEmployee(sample("ravi@example.com"));

        assertNotNull(saved.getId());
        assertEquals(1, service.getAllEmployees().size());
    }

    @Test
    void registerEmployee_duplicateEmail_throwsException() {
        service.registerEmployee(sample("ravi@example.com"));

        assertThrows(DuplicateEmployeeException.class,
                () -> service.registerEmployee(sample("RAVI@example.com")));
        assertEquals(1, service.getAllEmployees().size());
    }

    @Test
    void registerEmployee_null_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> service.registerEmployee(null));
    }

    @Test
    void getAllEmployees_emptyAtStart() {
        assertTrue(service.getAllEmployees().isEmpty());
    }

    @Test
    void getAllEmployees_returnsInRegistrationOrder() {
        service.registerEmployee(sample("a@example.com"));
        service.registerEmployee(sample("b@example.com"));

        assertEquals("a@example.com", service.getAllEmployees().get(0).getEmail());
        assertEquals("b@example.com", service.getAllEmployees().get(1).getEmail());
    }
}
