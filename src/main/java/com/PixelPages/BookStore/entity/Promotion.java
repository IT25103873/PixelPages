package com.PixelPages.BookStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Promotions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    @Id
    @Column(name = "promo_id", length = 10)
    private String promoId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "discount_pct", nullable = false, columnDefinition = "DECIMAL(5,2)")
    private double discountPct;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}