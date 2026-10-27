package com.example.employee.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.employee.aop.AuditLogStore;
import com.example.employee.exception.DuplicateEmployeeException;
import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;

/** Controller: handles web requests and picks the JSP view. No business logic here. */
@Controller
public class EmployeeController {

    private static final List<String> DEPARTMENTS =
            List.of("Engineering", "HR", "Finance", "Marketing", "Sales", "Operations");

    private final EmployeeService employeeService;
    private final AuditLogStore auditLogStore;

    public EmployeeController(EmployeeService employeeService, AuditLogStore auditLogStore) {
        this.employeeService = employeeService;
        this.auditLogStore = auditLogStore;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/employees";
    }

    @GetMapping("/employees")
    public String list(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee-list";
    }

    @GetMapping("/employees/new")
    public String showForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", DEPARTMENTS);
        return "employee-form";
    }

    @PostMapping("/employees")
    public String save(@Valid @ModelAttribute("employee") Employee employee,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        model.addAttribute("departments", DEPARTMENTS);

        if (result.hasErrors()) {
            return "employee-form";
        }
        try {
            employeeService.registerEmployee(employee);
        } catch (DuplicateEmployeeException ex) {
            result.rejectValue("email", "duplicate", ex.getMessage());
            return "employee-form";
        }
        redirectAttributes.addFlashAttribute("success",
                "Employee '" + employee.getName() + "' registered successfully!");
        return "redirect:/employees";
    }

    @GetMapping("/audit")
    public String audit(Model model) {
        model.addAttribute("entries", auditLogStore.getEntries());
        return "audit";
    }
}
