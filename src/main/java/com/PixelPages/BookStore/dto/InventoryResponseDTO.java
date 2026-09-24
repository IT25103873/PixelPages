package com.PixelPages.BookStore.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryResponseDTO {
    private Integer id;
    private Integer bookId;
    private String bookTitle;
    private Integer stockQuantity;
    private Integer reorderLevel;
    private LocalDateTime lastUpdated;
}