package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("SELECT c.customerId FROM Customer c ORDER BY c.customerId DESC LIMIT 1")
    Optional<String> findLastCustomerId();

    Optional<Customer> findByEmail(String email);
}