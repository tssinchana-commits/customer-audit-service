package com.project.findisc.customer_audit.service;

import com.project.findisc.customer_audit.entity.CustomerAuditEntity;
import com.project.findisc.customer_audit.repository.CustomerAuditRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAuditService {

    private final CustomerAuditRepository customerAuditRepository;

    public CustomerAuditService(CustomerAuditRepository customerAuditRepository) {
        this.customerAuditRepository = customerAuditRepository;
    }

    public List<CustomerAuditEntity> getAllCustomers() {
        return customerAuditRepository.findAll();
    }

    public CustomerAuditEntity getCustomerById(Long customerId) {
        return customerAuditRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public CustomerAuditEntity createCustomer(CustomerAuditEntity entity) {
        return customerAuditRepository.save(entity);
    }

    public CustomerAuditEntity updateCustomer(Long customerId, CustomerAuditEntity entity) {
        CustomerAuditEntity existing = getCustomerById(customerId);
        existing.setAction(entity.getAction());
        existing.setReason(entity.getReason());
        return customerAuditRepository.save(existing);
    }

    public void deleteCustomer(Long customerId) {
        customerAuditRepository.deleteById(customerId);
    }
}