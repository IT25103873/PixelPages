package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query("SELECT COALESCE(SUM(oi.quantity), 0) FROM OrderItem oi " +
            "JOIN Order o ON oi.orderId = o.orderId " +
            "WHERE CAST(o.orderDate AS date) = CURRENT_DATE")
    Long countBooksSoldToday();

    @Query("SELECT COALESCE(SUM(oi.quantity * oi.unitPrice), 0.0) FROM OrderItem oi " +
            "JOIN Order o ON oi.orderId = o.orderId " +
            "WHERE CAST(o.orderDate AS date) = CURRENT_DATE")
    Double sumRevenueToday();

    @Query("SELECT CAST(o.orderDate AS date), SUM(oi.quantity), SUM(oi.quantity * oi.unitPrice) " +
            "FROM OrderItem oi JOIN Order o ON oi.orderId = o.orderId " +
            "WHERE o.orderDate >= :fromDate " +
            "GROUP BY CAST(o.orderDate AS date) ORDER BY CAST(o.orderDate AS date) ASC")
    List<Object[]> getDailyTrend(@Param("fromDate") LocalDateTime fromDate);

    @Query("SELECT CONCAT(CAST(YEAR(o.orderDate) AS string), '-W', CAST(WEEK(o.orderDate) AS string)), SUM(oi.quantity), SUM(oi.quantity * oi.unitPrice) " +
            "FROM OrderItem oi JOIN Order o ON oi.orderId = o.orderId " +
            "WHERE o.orderDate >= :fromDate " +
            "GROUP BY CONCAT(CAST(YEAR(o.orderDate) AS string), '-W', CAST(WEEK(o.orderDate) AS string)) " +
            "ORDER BY CONCAT(CAST(YEAR(o.orderDate) AS string), '-W', CAST(WEEK(o.orderDate) AS string)) ASC")
    List<Object[]> getWeeklyTrend(@Param("fromDate") LocalDateTime fromDate);

    @Query("SELECT FUNCTION('FORMAT', o.orderDate, 'yyyy-MM'), SUM(oi.quantity), SUM(oi.quantity * oi.unitPrice) " +
            "FROM OrderItem oi JOIN Order o ON oi.orderId = o.orderId " +
            "WHERE o.orderDate >= :fromDate " +
            "GROUP BY FUNCTION('FORMAT', o.orderDate, 'yyyy-MM') ORDER BY FUNCTION('FORMAT', o.orderDate, 'yyyy-MM') ASC")
    List<Object[]> getMonthlyTrend(@Param("fromDate") LocalDateTime fromDate);
}