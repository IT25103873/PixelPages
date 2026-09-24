package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByBook_Id(Integer bookId);
}