package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyPromoDTO {
    private String code;
    private double originalPrice;
}