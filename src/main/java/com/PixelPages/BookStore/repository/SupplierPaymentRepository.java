package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.SupplierPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierPaymentRepository extends JpaRepository<SupplierPayment, Integer> {
}
