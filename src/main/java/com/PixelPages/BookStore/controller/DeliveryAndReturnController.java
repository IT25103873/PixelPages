package com.PixelPages.BookStore.controller;

import com.PixelPages.BookStore.dto.DeliveryStatusUpdateRequest;
import com.PixelPages.BookStore.dto.ReturnRequestDTO;
import com.PixelPages.BookStore.entity.Delivery;
import com.PixelPages.BookStore.entity.ReturnExchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.PixelPages.BookStore.service.DeliveryAndReturnService;

@RestController
@RequestMapping("/api")
public class DeliveryAndReturnController {

    private final DeliveryAndReturnService deliveryService;

    public DeliveryAndReturnController(DeliveryAndReturnService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping("/deliveries/track/{trackingNumber}")
    public ResponseEntity<Delivery> trackDelivery(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(deliveryService.trackDelivery(trackingNumber));
    }

    @PutMapping("/deliveries/{deliveryId}/status")
    public ResponseEntity<Delivery> updateDeliveryStatus(@PathVariable Long deliveryId,
                                                         @RequestBody DeliveryStatusUpdateRequest request) {
        return ResponseEntity.ok(deliveryService.updateDeliveryStatus(deliveryId, request));
    }

    @PostMapping("/returns")
    public ResponseEntity<ReturnExchange> requestReturn(@RequestBody ReturnRequestDTO request) {
        return new ResponseEntity<>(deliveryService.submitReturnRequest(request), HttpStatus.CREATED);
    }
}