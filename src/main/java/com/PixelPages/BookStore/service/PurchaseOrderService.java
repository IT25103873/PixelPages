package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.PurchaseOrderRequest;
import com.PixelPages.BookStore.dto.PurchaseOrderResponse;
import com.PixelPages.BookStore.dto.SupplierPaymentRequest;
import com.PixelPages.BookStore.dto.SupplierPaymentResponse;

import java.util.List;

public interface PurchaseOrderService {
    PurchaseOrderResponse createPurchaseOrder(PurchaseOrderRequest request);
    PurchaseOrderResponse markAsReceived(Integer purchaseOrderId);
    SupplierPaymentResponse createSupplierPayment(Integer purchaseOrderId, SupplierPaymentRequest request);
    List<PurchaseOrderResponse> getAllPurchaseOrders();
    PurchaseOrderResponse getPurchaseOrderById(Integer purchaseOrderId);
    List<SupplierPaymentResponse> getAllSupplierPayments();
}
