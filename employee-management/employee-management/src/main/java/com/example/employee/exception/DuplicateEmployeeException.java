package com.example.employee.exception;

/** Thrown when an employee with the same email already exists. */
public class DuplicateEmployeeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateEmployeeException(String message) {
        super(message);
    }
}
