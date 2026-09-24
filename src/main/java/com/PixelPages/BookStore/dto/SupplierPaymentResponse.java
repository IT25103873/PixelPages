package com.PixelPages.BookStore.dto;

import com.PixelPages.BookStore.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierPaymentResponse {
    private Integer paymentId;
    private Integer purchaseOrderId;
    private Integer supplierId;
    private String supplierName;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
}
