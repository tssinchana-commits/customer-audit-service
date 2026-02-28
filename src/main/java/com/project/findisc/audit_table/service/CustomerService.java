package com.project.findisc.audit_table.service;

import org.springframework.stereotype.Service;
import com.project.findisc.audit_table.enums.UserRole;
import com.project.findisc.audit_table.entity.CustomerEntity;
import com.project.findisc.audit_table.enums.CustomerStatus;
import com.project.findisc.audit_table.repository.CustomerRepository;
import com.project.findisc.audit_table.exception.CustomException;

import java.time.LocalDateTime;
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
                .orElseThrow(() -> new CustomException("Customer not found", 404));
    }

    // ✅ Set default status while creating
    public CustomerEntity createCustomer(CustomerEntity customer) {
        customer.setCustomerStatus(CustomerStatus.SUBMITTED);
        customer.setCreatedBy("REPRESENTATIVE"); // or logged user
        return customerRepository.save(customer);
    }

    public CustomerEntity updateCustomer(Long id, CustomerEntity updatedCustomer) {

        CustomerEntity existing = customerRepository.findById(id)
                .orElseThrow(() -> new CustomException("Customer not found", 404));

        existing.setName(updatedCustomer.getName());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setKyc(updatedCustomer.getKyc());
        existing.setAadhaar(updatedCustomer.getAadhaar());
        existing.setPan(updatedCustomer.getPan());
        existing.setPhoto(updatedCustomer.getPhoto());

        return customerRepository.save(existing);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // ✅ STATUS FLOW LOGIC
    public void updateCustomerStatus(Long id,
            CustomerStatus newStatus,
            UserRole role,
            String username,
            String remarks) {

        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomException("Customer not found", 404));

        switch (role) {

            // REPRESENTATIVE
            case REPRESENTATIVE:

                if (newStatus == CustomerStatus.SUBMITTED) {
                    customer.setCustomerStatus(CustomerStatus.SUBMITTED);
                    customer.setCreatedBy(username);
                } else {
                    throw new CustomException("Representative can only submit", 400);
                }
                break;

            // VERIFIER
            case VERIFICATION:

                if (newStatus == CustomerStatus.VERIFIED &&
                        customer.getCustomerStatus() == CustomerStatus.SUBMITTED) {

                    customer.setCustomerStatus(CustomerStatus.VERIFIED);
                    customer.setVerifiedBy(username);
                    customer.setVerifiedAt(LocalDateTime.now());
                    customer.setRemarks(remarks);
                }

                else if (newStatus == CustomerStatus.REJECTED &&
                        customer.getCustomerStatus() == CustomerStatus.SUBMITTED) {

                    customer.setCustomerStatus(CustomerStatus.REJECTED);
                    customer.setVerifiedBy(username);
                    customer.setVerifiedAt(LocalDateTime.now());
                    customer.setRemarks(remarks);
                }

                else if (newStatus == CustomerStatus.SUBMITTED &&
                        customer.getCustomerStatus() == CustomerStatus.REJECTED) {

                    // Resubmission
                    customer.setCustomerStatus(CustomerStatus.SUBMITTED);
                    customer.setVerifiedBy(null);
                    customer.setVerifiedAt(null);
                    customer.setApprovedBy(null);
                    customer.setApprovedAt(null);
                    customer.setRemarks("Resubmitted by " + username);
                }

                else {
                    throw new CustomException("Invalid status transition for Verifier", 400);
                }
                break;

            // MANAGER
            case MANAGER:

                if (newStatus == CustomerStatus.ACTIVE &&
                        customer.getCustomerStatus() == CustomerStatus.VERIFIED) {

                    customer.setCustomerStatus(CustomerStatus.ACTIVE);
                    customer.setApprovedBy(username);
                    customer.setApprovedAt(LocalDateTime.now());
                    customer.setRemarks(remarks);
                }

                else if (newStatus == CustomerStatus.REJECTED &&
                        customer.getCustomerStatus() == CustomerStatus.VERIFIED) {

                    customer.setCustomerStatus(CustomerStatus.REJECTED);
                    customer.setApprovedBy(username);
                    customer.setApprovedAt(LocalDateTime.now());
                    customer.setRemarks(remarks);
                }

                else {
                    throw new CustomException("Invalid status transition for Manager", 400);
                }
                break;

            default:
                throw new CustomException("Unknown role", 400);
        }

        customerRepository.save(customer);
    }
}