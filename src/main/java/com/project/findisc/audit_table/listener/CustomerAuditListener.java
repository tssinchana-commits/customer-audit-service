package com.project.findisc.audit_table.listener;

import com.project.findisc.audit_table.entity.CustomerAuditEntity;
import com.project.findisc.audit_table.entity.CustomerEntity;
import com.project.findisc.audit_table.repository.CustomerAuditRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

public class CustomerAuditListener {

    private static CustomerAuditRepository auditRepository;

    @Autowired
    public void setAuditRepository(CustomerAuditRepository repository) {
        // ✅ Assign to static field from non-static method
        auditRepository = repository;
    }

    @PostPersist
    public void afterCreate(CustomerEntity customer) {
        saveAudit(customer, "CREATE", null);
    }

    @PostUpdate
    public void afterUpdate(CustomerEntity customer) {
        saveAudit(customer, "UPDATE", null);
    }

    @PostRemove
    public void afterDelete(CustomerEntity customer) {
        saveAudit(customer, "DELETE", "Deleted by system");
    }

    private void saveAudit(CustomerEntity customer, String action, String reason) {
        CustomerAuditEntity audit = new CustomerAuditEntity();
        audit.setEntityType("Customer");
        audit.setEntityId(customer.getId());
        audit.setAction(action);
        audit.setReason(reason);
        audit.setCreatedAt(LocalDateTime.now());

        audit.setData("""
                {
                  "name": "%s",
                  "email": "%s",
                  "phone": "%s"
                }
                """.formatted(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()));

        auditRepository.save(audit);
    }
}