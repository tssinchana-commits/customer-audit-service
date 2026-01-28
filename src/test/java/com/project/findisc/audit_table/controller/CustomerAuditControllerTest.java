package com.project.findisc.audit_table.controller;

import com.project.findisc.audit_table.entity.AuditEntity;
import com.project.findisc.audit_table.repository.AuditRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditController.class)
class CustomerAuditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditRepository auditRepository;

    @Test
    void getAllAudits_success() throws Exception {

        AuditEntity audit = new AuditEntity();
        audit.setEntityType("Customer");
        audit.setEntityId(1L);
        audit.setAction("CREATE");

        Mockito.when(auditRepository.findAll())
                .thenReturn(List.of(audit));

        mockMvc.perform(get("/customer/api/v1/audits"))
                .andExpect(status().isOk());
    }
}