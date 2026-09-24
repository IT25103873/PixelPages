package com.PixelPages.BookStore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Inventory")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false, unique = true)
    private Book book;

    @Column(name = "stock_quantity", nullable = false)
    @Builder.Default
    private Integer stockQuantity = 0;

    @Column(name = "reorder_level", nullable = false)
    @Builder.Default
    private Integer reorderLevel = 5;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;
}