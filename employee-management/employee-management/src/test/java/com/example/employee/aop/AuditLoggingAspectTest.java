package com.example.employee.aop;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.aop.support.AopUtils;

import com.example.employee.exception.DuplicateEmployeeException;
import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;

/** Shows that the aspect logs service calls even though the service has no logging code. */
class AuditLoggingAspectTest {

    @Configuration
    @EnableAspectJAutoProxy
    @ComponentScan(basePackages = {
            "com.example.employee.service",
            "com.example.employee.repository",
            "com.example.employee.aop" })
    static class TestConfig {
    }

    private AnnotationConfigApplicationContext context;
    private EmployeeService service;
    private AuditLogStore store;

    @BeforeEach
    void setUp() {
        context = new AnnotationConfigApplicationContext(TestConfig.class);
        service = context.getBean(EmployeeService.class);
        store = context.getBean(AuditLogStore.class);
    }

    @AfterEach
    void tearDown() {
        context.close();
    }

    private Employee sample() {
        return new Employee("Meena S", "meena@example.com", "9000000001", "Finance",
                new BigDecimal("60000"), LocalDate.of(2022, 3, 10));
    }

    @Test
    void serviceBeanIsProxied() {
        assertEquals(true, AopUtils.isAopProxy(service));
    }

    @Test
    void successfulCall_isAudited() {
        service.registerEmployee(sample());

        List<AuditEntry> entries = store.getEntries();
        assertEquals(1, entries.size());
        assertEquals("registerEmployee", entries.get(0).operation());
        assertEquals("SUCCESS", entries.get(0).status());
    }

    @Test
    void failedCall_isAuditedAsFailed() {
        service.registerEmployee(sample());
        store.clear();

        assertThrows(DuplicateEmployeeException.class, () -> service.registerEmployee(sample()));

        assertEquals(1, store.getEntries().size());
        assertEquals("FAILED", store.getEntries().get(0).status());
    }

    @Test
    void listCall_isAudited() {
        service.getAllEmployees();

        assertEquals("getAllEmployees", store.getEntries().get(0).operation());
    }
}
