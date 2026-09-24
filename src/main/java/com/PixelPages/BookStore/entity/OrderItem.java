package com.PixelPages.BookStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OrderItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Integer orderItemId;

    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;
}