package com.PixelPages.BookStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "address_id", nullable = false)
    private Integer addressId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;
}