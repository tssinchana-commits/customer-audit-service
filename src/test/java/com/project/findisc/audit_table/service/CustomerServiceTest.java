package com.project.findisc.audit_table.service;

import com.project.findisc.audit_table.entity.CustomerEntity;
import com.project.findisc.audit_table.enums.CustomerStatus;
import com.project.findisc.audit_table.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void testRepresentativeSubmitsCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerStatus(CustomerStatus.SUBMITTED);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Representative action: SUBMITTED
        customerService.updateCustomerStatus(1L, CustomerStatus.SUBMITTED, "REPRESENTATIVE", "RepUser", null);

        assertEquals(CustomerStatus.SUBMITTED, customer.getCustomerStatus());
        assertEquals("RepUser", customer.getCreatedBy());
        verify(customerRepository).save(customer);
    }

    @Test
    void testVerifierApprovesCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerStatus(CustomerStatus.SUBMITTED);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        customerService.updateCustomerStatus(1L, CustomerStatus.VERIFIED, "VERIFICATION", "VerifierUser", "All good");

        assertEquals(CustomerStatus.VERIFIED, customer.getCustomerStatus());
        assertEquals("VerifierUser", customer.getVerifiedBy());
        assertNotNull(customer.getVerifiedAt());
        assertEquals("All good", customer.getRemarks());
        verify(customerRepository).save(customer);
    }

    @Test
    void testManagerApprovesCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerStatus(CustomerStatus.VERIFIED);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        customerService.updateCustomerStatus(1L, CustomerStatus.ACTIVE, "MANAGER", "ManagerUser", "Approved");

        assertEquals(CustomerStatus.ACTIVE, customer.getCustomerStatus());
        assertEquals("ManagerUser", customer.getApprovedBy());
        assertNotNull(customer.getApprovedAt());
        assertEquals("Approved", customer.getRemarks());
        verify(customerRepository).save(customer);
    }

    @Test
    void testInvalidTransitionThrows() {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerStatus(CustomerStatus.SUBMITTED);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerService.updateCustomerStatus(1L, CustomerStatus.ACTIVE, "MANAGER", "ManagerUser", null);
        });

        assertTrue(exception.getMessage().contains("Invalid status transition"));
    }
}