package com.project.findisc.audit_table.controller;

import com.project.findisc.audit_table.entity.CustomerAuditEntity;
import com.project.findisc.audit_table.repository.CustomerAuditRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/api/v1/audits")
public class CustomerAuditController {

    private final CustomerAuditRepository auditRepository;

    public CustomerAuditController(CustomerAuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @GetMapping
    public List<CustomerAuditEntity> getAllAudits() {
        return auditRepository.findAll();
    }

    @GetMapping("/{id}")
    public CustomerAuditEntity getAudit(@PathVariable Long id) {
        return auditRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audit not found"));
    }
}