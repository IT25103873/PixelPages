package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByBook_Id(Integer bookId);

    @Query("SELECT i FROM Inventory i WHERE i.stockQuantity <= i.reorderLevel")
    List<Inventory> findLowStockItems();
}