package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequestDTO {
    private String name;
    private double discountPct;
    private Integer categoryId;
    private LocalDate startDate;
    private LocalDate endDate;
}