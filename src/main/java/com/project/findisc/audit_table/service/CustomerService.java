package com.project.findisc.audit_table.service;

import org.springframework.stereotype.Service;
import com.project.findisc.audit_table.entity.CustomerEntity;
import com.project.findisc.audit_table.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerEntity getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public CustomerEntity createCustomer(CustomerEntity customer) {
        return customerRepository.save(customer);
    }

    public CustomerEntity updateCustomer(Long id, CustomerEntity updatedCustomer) {

        CustomerEntity existing = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existing.setName(updatedCustomer.getName());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setStatus(updatedCustomer.getStatus());
        existing.setKyc(updatedCustomer.getKyc());
        existing.setAadhaar(updatedCustomer.getAadhaar());
        existing.setPan(updatedCustomer.getPan());
        existing.setPhoto(updatedCustomer.getPhoto());

        return customerRepository.save(existing);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}