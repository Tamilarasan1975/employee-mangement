package com.example.employee.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.example.employee.aop.AuditLogStore;
import com.example.employee.repository.InMemoryEmployeeRepository;
import com.example.employee.service.EmployeeServiceImpl;

class EmployeeControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        EmployeeController controller = new EmployeeController(
                new EmployeeServiceImpl(new InMemoryEmployeeRepository()), new AuditLogStore());
        InternalResourceViewResolver resolver = new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(resolver).build();
    }

    @Test
    void listPage_isShown() throws Exception {
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-list"))
                .andExpect(model().attributeExists("employees"));
    }

    @Test
    void registrationForm_isShown() throws Exception {
        mockMvc.perform(get("/employees/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-form"))
                .andExpect(model().attributeExists("employee", "departments"));
    }

    @Test
    void validSubmission_redirectsToList() throws Exception {
        mockMvc.perform(post("/employees")
                        .param("name", "Kiran Raj")
                        .param("email", "kiran@example.com")
                        .param("phone", "9876501234")
                        .param("department", "Sales")
                        .param("salary", "45000")
                        .param("joiningDate", "2024-02-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employees"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    void invalidSubmission_returnsFormWithErrors() throws Exception {
        mockMvc.perform(post("/employees")
                        .param("name", "")
                        .param("email", "bad-email")
                        .param("phone", "123")
                        .param("department", "")
                        .param("salary", "abc")
                        .param("joiningDate", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("employee-form"))
                .andExpect(model().attributeHasFieldErrors("employee",
                        "name", "email", "phone", "department", "salary", "joiningDate"));
    }

    @Test
    void duplicateEmail_showsErrorOnEmailField() throws Exception {
        var request = post("/employees")
                .param("name", "Kiran Raj")
                .param("email", "kiran@example.com")
                .param("phone", "9876501234")
                .param("department", "Sales")
                .param("salary", "45000")
                .param("joiningDate", "2024-02-01");

        mockMvc.perform(request).andExpect(status().is3xxRedirection());
        mockMvc.perform(request)
                .andExpect(view().name("employee-form"))
                .andExpect(model().attributeHasFieldErrors("employee", "email"));
    }
}
