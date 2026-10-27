package com.example.employee.aop;

   import java.time.LocalDateTime;
   import java.time.format.DateTimeFormatter;

   public class AuditEntry {

       private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

       private final LocalDateTime time;
       private final String operation;
       private final String status;
       private final String details;

       public AuditEntry(LocalDateTime time, String operation, String status, String details) {
           this.time = time;
           this.operation = operation;
           this.status = status;
           this.details = details;
       }

       public LocalDateTime getTime() { return time; }
       public String getFormattedTime() { return time.format(FORMAT); }
       public String getOperation() { return operation; }
       public String getStatus() { return status; }
       public String getDetails() { return details; }
   }