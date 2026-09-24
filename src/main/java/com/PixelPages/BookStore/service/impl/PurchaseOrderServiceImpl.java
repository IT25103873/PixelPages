package com.PixelPages.BookStore.service.impl;

import com.PixelPages.BookStore.dto.*;
import com.PixelPages.BookStore.entity.*;
import com.PixelPages.BookStore.repository.BookRepository;
import com.PixelPages.BookStore.repository.PurchaseOrderRepository;
import com.PixelPages.BookStore.repository.SupplierPaymentRepository;
import com.PixelPages.BookStore.repository.SupplierRepository;
import com.PixelPages.BookStore.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierPaymentRepository supplierPaymentRepository;
    private final SupplierRepository supplierRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public PurchaseOrderResponse createPurchaseOrder(PurchaseOrderRequest request) {
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + request.getSupplierId()));

        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .supplier(supplier)
                .status(PurchaseOrderStatus.PENDING)
                .orderDate(LocalDateTime.now())
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (PurchaseOrderItemRequest itemRequest : request.getItems()) {
            Book book = bookRepository.findById(itemRequest.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found with id: " + itemRequest.getBookId()));

            PurchaseOrderItem item = PurchaseOrderItem.builder()
                    .book(book)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(itemRequest.getUnitPrice())
                    .build();

            purchaseOrder.addItem(item);
            totalAmount = totalAmount.add(itemRequest.getUnitPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }

        purchaseOrder.setTotalAmount(totalAmount);

        PurchaseOrder savedOrder = purchaseOrderRepository.save(purchaseOrder);
        return mapToPurchaseOrderResponse(savedOrder);
    }

    @Override
    @Transactional
    public PurchaseOrderResponse markAsReceived(Integer purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with id: " + purchaseOrderId));

        if (purchaseOrder.getStatus() == PurchaseOrderStatus.RECEIVED) {
            throw new RuntimeException("Purchase Order is already marked as received.");
        }

        // Update book stock quantities
        for (PurchaseOrderItem item : purchaseOrder.getItems()) {
            Book book = item.getBook();
            int newQuantity = (book.getStockQuantity() != null ? book.getStockQuantity() : 0) + item.getQuantity();
            book.setStockQuantity(newQuantity);
            bookRepository.save(book);
        }

        purchaseOrder.setStatus(PurchaseOrderStatus.RECEIVED);
        PurchaseOrder updatedOrder = purchaseOrderRepository.save(purchaseOrder);
        
        return mapToPurchaseOrderResponse(updatedOrder);
    }

    @Override
    @Transactional
    public SupplierPaymentResponse createSupplierPayment(Integer purchaseOrderId, SupplierPaymentRequest request) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with id: " + purchaseOrderId));

        SupplierPayment payment = SupplierPayment.builder()
                .purchaseOrder(purchaseOrder)
                .supplier(purchaseOrder.getSupplier())
                .amount(request.getAmount())
                .paymentDate(LocalDateTime.now())
                .status(PaymentStatus.PAID)
                .build();

        SupplierPayment savedPayment = supplierPaymentRepository.save(payment);
        return mapToSupplierPaymentResponse(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrderResponse> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll().stream()
                .map(this::mapToPurchaseOrderResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseOrderResponse getPurchaseOrderById(Integer purchaseOrderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with id: " + purchaseOrderId));
        return mapToPurchaseOrderResponse(purchaseOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierPaymentResponse> getAllSupplierPayments() {
        return supplierPaymentRepository.findAll().stream()
                .map(this::mapToSupplierPaymentResponse)
                .collect(Collectors.toList());
    }

    private PurchaseOrderResponse mapToPurchaseOrderResponse(PurchaseOrder order) {
        List<PurchaseOrderItemResponse> itemResponses = order.getItems().stream().map(item ->
                PurchaseOrderItemResponse.builder()
                        .itemId(item.getItemId())
                        .bookId(item.getBook().getId())
                        .bookTitle(item.getBook().getTitle())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build()
        ).collect(Collectors.toList());

        return PurchaseOrderResponse.builder()
                .purchaseOrderId(order.getPurchaseOrderId())
                .supplierId(order.getSupplier().getId())
                .supplierName(order.getSupplier().getName())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(itemResponses)
                .build();
    }

    private SupplierPaymentResponse mapToSupplierPaymentResponse(SupplierPayment payment) {
        return SupplierPaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .purchaseOrderId(payment.getPurchaseOrder().getPurchaseOrderId())
                .supplierId(payment.getSupplier().getId())
                .supplierName(payment.getSupplier().getName())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .status(payment.getStatus())
                .build();
    }
}
