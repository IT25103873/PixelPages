package com.PixelPages.BookStore.dto;

import com.PixelPages.BookStore.entity.BookFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookResponseDTO {
    private Integer id;
    private String title;
    private String author;
    private String isbn;

    private Integer categoryId;
    private String categoryName;

    private Integer supplierId;
    private String supplierName;

    private Integer sellerId;

    private BookFormat format;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
}