package com.project.findisc.customer_audit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.findisc.customer_audit.entity.CustomerAuditEntity;
import com.project.findisc.customer_audit.service.CustomerAuditService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerAuditController.class)
class CustomerAuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerAuditService customerAuditService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllAudits_success() throws Exception {

        CustomerAuditEntity audit = new CustomerAuditEntity();
        audit.setCustomerId(1L);
        audit.setAction("CREATE");

        // ✅ METHOD NAME MATCHES SERVICE
        Mockito.when(customerAuditService.getAllCustomers())
                .thenReturn(List.of(audit));

        mockMvc.perform(get("/customer/api/v1/customers"))
                .andExpect(status().isOk());
    }

    @Test
    void createAudit_success() throws Exception {

        CustomerAuditEntity audit = new CustomerAuditEntity();
        audit.setCustomerId(2L);
        audit.setAction("DELETE");
        audit.setReason("User requested");

        // ✅ METHOD NAME MATCHES SERVICE
        Mockito.when(customerAuditService.createCustomer(Mockito.any()))
                .thenReturn(audit);

        mockMvc.perform(post("/customer/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(audit)))
                .andExpect(status().isCreated());
    }
}