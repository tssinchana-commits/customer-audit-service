package com.project.findisc.customer_audit.controller;

import com.project.findisc.customer_audit.entity.CustomerAuditEntity;
import com.project.findisc.customer_audit.service.CustomerAuditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/api/v1/customers")
public class CustomerAuditController {

    private final CustomerAuditService customerAuditService;

    public CustomerAuditController(CustomerAuditService customerAuditService) {
        this.customerAuditService = customerAuditService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerAuditEntity>> getAllCustomers() {
        return ResponseEntity.ok(customerAuditService.getAllCustomers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerAuditEntity> getCustomerById(
            @PathVariable Long customerId) {
        return ResponseEntity.ok(customerAuditService.getCustomerById(customerId));
    }

    @PostMapping
    public ResponseEntity<CustomerAuditEntity> createCustomer(
            @RequestBody CustomerAuditEntity customerAuditEntity) {
        return new ResponseEntity<>(
                customerAuditService.createCustomer(customerAuditEntity),
                HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerAuditEntity> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerAuditEntity customerAuditEntity) {
        return ResponseEntity.ok(
                customerAuditService.updateCustomer(customerId, customerAuditEntity));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long customerId) {
        customerAuditService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}