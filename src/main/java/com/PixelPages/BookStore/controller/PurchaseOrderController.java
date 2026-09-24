package com.PixelPages.BookStore.controller;

import com.PixelPages.BookStore.dto.PurchaseOrderRequest;
import com.PixelPages.BookStore.dto.PurchaseOrderResponse;
import com.PixelPages.BookStore.dto.SupplierPaymentRequest;
import com.PixelPages.BookStore.dto.SupplierPaymentResponse;
import com.PixelPages.BookStore.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @PostMapping("/purchase-orders")
    public ResponseEntity<PurchaseOrderResponse> createPurchaseOrder(@RequestBody PurchaseOrderRequest request) {
        PurchaseOrderResponse response = purchaseOrderService.createPurchaseOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/purchase-orders")
    public ResponseEntity<List<PurchaseOrderResponse>> getAllPurchaseOrders() {
        return ResponseEntity.ok(purchaseOrderService.getAllPurchaseOrders());
    }

    @GetMapping("/purchase-orders/{id}")
    public ResponseEntity<PurchaseOrderResponse> getPurchaseOrderById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderById(id));
    }

    @PatchMapping("/purchase-orders/{id}/receive")
    public ResponseEntity<PurchaseOrderResponse> markAsReceived(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(purchaseOrderService.markAsReceived(id));
    }

    @PostMapping("/purchase-orders/{id}/payment")
    public ResponseEntity<SupplierPaymentResponse> createSupplierPayment(
            @PathVariable("id") Integer id,
            @RequestBody SupplierPaymentRequest request) {
        SupplierPaymentResponse response = purchaseOrderService.createSupplierPayment(id, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/supplier-payments")
    public ResponseEntity<List<SupplierPaymentResponse>> getAllSupplierPayments() {
        return ResponseEntity.ok(purchaseOrderService.getAllSupplierPayments());
    }
}
