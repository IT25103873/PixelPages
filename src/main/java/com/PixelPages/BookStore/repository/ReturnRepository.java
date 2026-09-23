package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReturnRepository extends JpaRepository<Return, Integer> {

    @Query("SELECT COUNT(r) FROM Return r WHERE CAST(r.requestedAt AS date) = CURRENT_DATE")
    Long countReturnsToday();

    @Query("SELECT COALESCE(SUM(oi.quantity * oi.unitPrice), 0.0) FROM Return r " +
            "JOIN OrderItem oi ON r.orderItemId = oi.orderItemId " +
            "WHERE CAST(r.requestedAt AS date) = CURRENT_DATE")
    Double sumRefundAmountToday();
}