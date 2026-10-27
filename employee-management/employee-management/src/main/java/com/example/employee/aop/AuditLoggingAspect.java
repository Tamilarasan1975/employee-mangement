package com.example.employee.aop;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.example.employee.model.Employee;

/**
 * Cross-cutting concern: audit logging.
 * Every call to the service layer is logged (start, success, or failure)
 * without touching the service code.
 */
@Aspect
@Component
public class AuditLoggingAspect {

    private static final Logger LOG = Logger.getLogger(AuditLoggingAspect.class.getName());

    private final AuditLogStore store;

    public AuditLoggingAspect(AuditLogStore store) {
        this.store = store;
    }

    @Pointcut("execution(* com.example.employee.service.EmployeeService.*(..))")
    public void serviceMethods() {
    }

    @Around("serviceMethods()")
    public Object audit(ProceedingJoinPoint pjp) throws Throwable {
        String operation = pjp.getSignature().getName();
        String details = describe(pjp.getArgs());
        LOG.info("[AUDIT] START " + operation + " " + details);
        try {
            Object result = pjp.proceed();
            record(operation, "SUCCESS", details);
            return result;
        } catch (Throwable ex) {
            record(operation, "FAILED", details + " -> " + ex.getMessage());
            throw ex;
        }
    }

    private void record(String operation, String status, String details) {
        LOG.info("[AUDIT] " + status + " " + operation + " " + details);
        store.add(new AuditEntry(LocalDateTime.now(), operation, status, details));
    }

    private String describe(Object[] args) {
        if (args.length == 0) {
            return "(no arguments)";
        }
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            if (arg instanceof Employee e) {
                sb.append("employee=").append(e.getName()).append(" <").append(e.getEmail()).append(">");
            } else {
                sb.append(arg);
            }
        }
        return sb.toString();
    }
}
