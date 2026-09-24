package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyPromoResponseDTO {
    private String promoId;
    private String code;
    private double originalPrice;
    private double discountAmount;
    private double finalPrice;
}