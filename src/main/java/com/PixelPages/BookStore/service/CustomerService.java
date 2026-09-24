package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.CustomerRequestDTO;
import com.PixelPages.BookStore.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
    CustomerResponseDTO registerCustomer(CustomerRequestDTO requestDTO);
    CustomerResponseDTO getCustomerById(String customerId);
    List<CustomerResponseDTO> getAllCustomers();
    CustomerResponseDTO updateCustomer(String customerId, CustomerRequestDTO requestDTO);
    void deleteCustomer(String customerId);
}