package com.example.employee.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EmployeeValidationTest {

    private static Validator validator;

    @BeforeAll
    static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private Employee valid() {
        return new Employee("Asha Devi", "asha@example.com", "9123456789", "HR",
                new BigDecimal("42000"), LocalDate.of(2023, 6, 1));
    }

    private Set<String> invalidFields(Employee e) {
        return validator.validate(e).stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(Object::toString)
                .collect(Collectors.toSet());
    }

    @Test
    void validEmployee_hasNoViolations() {
        assertTrue(validator.validate(valid()).isEmpty());
    }

    @Test
    void blankName_isRejected() {
        Employee e = valid();
        e.setName("  ");
        assertTrue(invalidFields(e).contains("name"));
    }

    @Test
    void invalidEmail_isRejected() {
        Employee e = valid();
        e.setEmail("not-an-email");
        assertEquals(Set.of("email"), invalidFields(e));
    }

    @Test
    void shortPhone_isRejected() {
        Employee e = valid();
        e.setPhone("12345");
        assertEquals(Set.of("phone"), invalidFields(e));
    }

    @Test
    void lowSalary_isRejected() {
        Employee e = valid();
        e.setSalary(new BigDecimal("10"));
        assertEquals(Set.of("salary"), invalidFields(e));
    }

    @Test
    void futureJoiningDate_isRejected() {
        Employee e = valid();
        e.setJoiningDate(LocalDate.now().plusDays(5));
        assertEquals(Set.of("joiningDate"), invalidFields(e));
    }

    @Test
    void emptyEmployee_reportsAllRequiredFields() {
        Set<String> fields = invalidFields(new Employee());
        assertTrue(fields.containsAll(
                Set.of("name", "email", "phone", "department", "salary", "joiningDate")));
    }
}
