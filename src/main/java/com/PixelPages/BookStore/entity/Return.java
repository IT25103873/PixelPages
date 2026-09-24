package com.PixelPages.BookStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ReturnsExchanges")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private Integer returnId;

    @Column(name = "order_item_id", nullable = false)
    private Integer orderItemId;

    @Column(length = 300)
    private String reason;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;
}