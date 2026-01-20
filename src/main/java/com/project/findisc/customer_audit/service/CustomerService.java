package com.project.findisc.customer_audit.service;

import com.project.findisc.customer_audit.entity.CustomerAuditEntity;
import com.project.findisc.customer_audit.entity.CustomerEntity;
import com.project.findisc.customer_audit.repository.CustomerAuditRepository;
import com.project.findisc.customer_audit.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerAuditRepository auditRepository;

    public CustomerService(CustomerRepository customerRepository,
            CustomerAuditRepository auditRepository) {
        this.customerRepository = customerRepository;
        this.auditRepository = auditRepository;
    }

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerEntity getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public CustomerEntity createCustomer(CustomerEntity customer) {
        CustomerEntity saved = customerRepository.save(customer);
        saveAudit(saved.getId(), "CREATE", null);
        return saved;
    }

    public CustomerEntity updateCustomer(Long id, CustomerEntity customer) {
        CustomerEntity existing = getCustomerById(id);
        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        existing.setPhone(customer.getPhone());

        CustomerEntity updated = customerRepository.save(existing);
        saveAudit(updated.getId(), "UPDATE", null);
        return updated;
    }

    public void deleteCustomer(Long id, String reason) {
        customerRepository.deleteById(id);
        saveAudit(id, "DELETE", reason);
    }

    private void saveAudit(Long customerId, String action, String reason) {
        CustomerAuditEntity audit = new CustomerAuditEntity();
        audit.setCustomerId(customerId);
        audit.setAction(action);
        audit.setReason(reason);
        auditRepository.save(audit);
    }
}