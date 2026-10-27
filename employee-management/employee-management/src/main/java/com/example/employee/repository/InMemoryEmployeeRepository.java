package com.example.employee.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.employee.model.Employee;

/** Simple in-memory storage (no database needed for this project). */
@Repository
public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final Map<Long, Employee> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(sequence.incrementAndGet());
        }
        store.put(employee.getId(), employee);
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> all = new ArrayList<>(store.values());
        all.sort(Comparator.comparing(Employee::getId));
        return all;
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return store.values().stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}
