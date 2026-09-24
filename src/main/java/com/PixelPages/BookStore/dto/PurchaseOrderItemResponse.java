package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemResponse {
    private Integer itemId;
    private Integer bookId;
    private String bookTitle;
    private Integer quantity;
    private BigDecimal unitPrice;
}
