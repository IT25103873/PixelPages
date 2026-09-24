package com.PixelPages.BookStore.dto;

import com.PixelPages.BookStore.entity.PurchaseOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderResponse {
    private Integer purchaseOrderId;
    private Integer supplierId;
    private String supplierName;
    private LocalDateTime orderDate;
    private PurchaseOrderStatus status;
    private BigDecimal totalAmount;
    private List<PurchaseOrderItemResponse> items;
}
