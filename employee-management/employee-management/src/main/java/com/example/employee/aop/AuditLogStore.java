package com.example.employee.aop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

/** Keeps audit entries in memory so they can be shown on the Audit page and checked in tests. */
@Component
public class AuditLogStore {

    private final List<AuditEntry> entries = new CopyOnWriteArrayList<>();

    public void add(AuditEntry entry) {
        entries.add(entry);
    }

    /** Newest first. */
    public List<AuditEntry> getEntries() {
        List<AuditEntry> copy = new ArrayList<>(entries);
        Collections.reverse(copy);
        return copy;
    }

    public void clear() {
        entries.clear();
    }
}
