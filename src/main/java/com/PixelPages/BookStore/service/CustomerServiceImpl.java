package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.CustomerRequestDTO;
import com.PixelPages.BookStore.dto.CustomerResponseDTO;
import com.PixelPages.BookStore.entity.Customer;
import com.PixelPages.BookStore.exception.CustomerNotFoundException;
import com.PixelPages.BookStore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private static final String PREFIX = "CUS";

    private String generateCustomerId() {
        String lastId = customerRepository.findLastCustomerId().orElse(null);
        int nextNumber = 1;
        if (lastId != null) {
            String numberPart = lastId.replace(PREFIX, "");
            nextNumber = Integer.parseInt(numberPart) + 1;
        }
        return PREFIX + String.format("%03d", nextNumber);
    }

    @Override
    public CustomerResponseDTO registerCustomer(CustomerRequestDTO requestDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(generateCustomerId());
        customer.setFirstName(requestDTO.getFirstName());
        customer.setLastName(requestDTO.getLastName());
        customer.setEmail(requestDTO.getEmail());
        customer.setPassword(requestDTO.getPassword());   // production - BCrypt encode කරන්න
        customer.setPhone(requestDTO.getPhone());
        customer.setAddress(requestDTO.getAddress());

        Customer saved = customerRepository.save(customer);
        return mapToResponseDTO(saved);
    }

    @Override
    public CustomerResponseDTO getCustomerById(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        return mapToResponseDTO(customer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO updateCustomer(String customerId, CustomerRequestDTO requestDTO) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

        customer.setFirstName(requestDTO.getFirstName());
        customer.setLastName(requestDTO.getLastName());
        customer.setEmail(requestDTO.getEmail());
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isBlank()) {
            customer.setPassword(requestDTO.getPassword());
        }
        customer.setPhone(requestDTO.getPhone());
        customer.setAddress(requestDTO.getAddress());

        Customer updated = customerRepository.save(customer);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));
        customerRepository.delete(customer);
    }

    private CustomerResponseDTO mapToResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress()
        );
    }
}