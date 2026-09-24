package com.PixelPages.BookStore.controller;

import com.PixelPages.BookStore.dto.CustomerLoginDTO;
import com.PixelPages.BookStore.dto.CustomerRequestDTO;
import com.PixelPages.BookStore.dto.CustomerResponseDTO;
import com.PixelPages.BookStore.entity.Customer;
import com.PixelPages.BookStore.exception.CustomerNotFoundException;
import com.PixelPages.BookStore.repository.CustomerRepository;
import com.PixelPages.BookStore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDTO> registerCustomer(@RequestBody CustomerRequestDTO requestDTO) {
        return new ResponseEntity<>(customerService.registerCustomer(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable String customerId,
                                                              @RequestBody CustomerRequestDTO requestDTO) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, requestDTO));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok("Customer deleted successfully with id: " + customerId);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerLoginDTO loginDTO) {
        Customer customer = customerRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new CustomerNotFoundException("Invalid email"));

        if (!customer.getPassword().equals(loginDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.ok(Map.of(
                "customerId", customer.getCustomerId(),
                "name", customer.getFirstName() + " " + customer.getLastName(),
                "email", customer.getEmail()
        ));
    }
}