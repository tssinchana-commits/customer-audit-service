package com.project.findisc.audit_table.controller;

import com.project.findisc.audit_table.entity.CustomerEntity;
import com.project.findisc.audit_table.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/customer/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // ✅ GET BY ID
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerEntity> getCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    // ✅ CREATE WITH FILE UPLOAD
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<CustomerEntity> createCustomer(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String status,
            @RequestParam String kyc,
            @RequestParam(required = false) String aadhaar,
            @RequestParam(required = false) String pan,
            @RequestParam(required = false) MultipartFile photo) throws IOException {

        CustomerEntity customer = new CustomerEntity();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setStatus(status);
        customer.setKyc(kyc);
        customer.setAadhaar(aadhaar);
        customer.setPan(pan);

        if (photo != null && !photo.isEmpty()) {
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            Files.write(filePath, photo.getBytes());
            customer.setSetPhoto(fileName);
        }

        return ResponseEntity.ok(customerService.createCustomer(customer));
    }

    // ✅ UPDATE WITH FILE UPLOAD
    @PutMapping(value = "/{customerId}", consumes = "multipart/form-data")
    public ResponseEntity<CustomerEntity> updateCustomer(
            @PathVariable Long customerId,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String status,
            @RequestParam String kyc,
            @RequestParam(required = false) String aadhaar,
            @RequestParam(required = false) String pan,
            @RequestParam(required = false) MultipartFile photo) throws IOException {

        CustomerEntity existing = customerService.getCustomerById(customerId);

        existing.setName(name);
        existing.setPhone(phone);
        existing.setStatus(status);
        existing.setKyc(kyc);
        existing.setAadhaar(aadhaar);
        existing.setPan(pan);

        if (photo != null && !photo.isEmpty()) {
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            Files.write(filePath, photo.getBytes());
            existing.setSetPhoto(fileName);
        }

        return ResponseEntity.ok(customerService.updateCustomer(customerId, existing));
    }

    // ✅ DELETE
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer deleted successfully");
    }
}