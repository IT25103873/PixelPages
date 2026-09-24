package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryStatusUpdateRequest {
    private String courierName;
    private String trackingNumber;
    private String status; // PROCESSING, DISPATCHED, IN_TRANSIT, DELIVERED, FAILED
}
