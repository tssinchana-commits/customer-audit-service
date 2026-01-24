package com.project.findisc.audit_table.controller;

import com.project.findisc.audit_table.entity.CustomerEntity;
import com.project.findisc.audit_table.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerEntity> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public CustomerEntity getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    public CustomerEntity createCustomer(@RequestBody CustomerEntity customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{customerId}")
    public CustomerEntity updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerEntity customer) {
        return customerService.updateCustomer(customerId, customer);
    }

    @DeleteMapping("/{customerId}")
    public String deleteCustomer(
            @PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return "Customer deleted successfully";
    }
}