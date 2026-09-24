package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.DeliveryStatusUpdateRequest;
import com.PixelPages.BookStore.dto.ReturnRequestDTO;
import com.PixelPages.BookStore.entity.Delivery;
import com.PixelPages.BookStore.entity.OrderItem;
import com.PixelPages.BookStore.entity.ReturnExchange;
import com.PixelPages.BookStore.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PixelPages.BookStore.repository.DeliveryRepository;
import com.PixelPages.BookStore.repository.OrderItemRepository;
import com.PixelPages.BookStore.repository.ReturnExchangeRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class DeliveryAndReturnService {

    private final DeliveryRepository deliveryRepository;
    private final ReturnExchangeRepository returnRepository;
    private final OrderItemRepository orderItemRepository;

    public DeliveryAndReturnService(DeliveryRepository deliveryRepository,
                                    ReturnExchangeRepository returnRepository,
                                    OrderItemRepository orderItemRepository) {
        this.deliveryRepository = deliveryRepository;
        this.returnRepository = returnRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Delivery trackDelivery(String trackingNumber) {
        return deliveryRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("No delivery found with tracking number: " + trackingNumber));
    }

    public Delivery updateDeliveryStatus(Long deliveryId, DeliveryStatusUpdateRequest request) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with ID: " + deliveryId));

        if (request.getStatus() != null) {
            delivery.setStatus(request.getStatus());
            if ("DISPATCHED".equalsIgnoreCase(request.getStatus())) {
                delivery.setDispatchedAt(LocalDateTime.now());
            } else if ("DELIVERED".equalsIgnoreCase(request.getStatus())) {
                delivery.setDeliveredAt(LocalDateTime.now());
            }
        }
        if (request.getCourierName() != null) {
            delivery.setCourierName(request.getCourierName());
        }
        if (request.getTrackingNumber() != null) {
            delivery.setTrackingNumber(request.getTrackingNumber());
        }

        return deliveryRepository.save(delivery);
    }

    public ReturnExchange submitReturnRequest(ReturnRequestDTO request) {
        OrderItem orderItem = orderItemRepository.findById(request.getOrderItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with ID: " + request.getOrderItemId()));

        ReturnExchange returnExchange = ReturnExchange.builder()
                .orderItem(orderItem)
                .requestType(request.getRequestType().toUpperCase())
                .reason(request.getReason())
                .status("REQUESTED")
                .requestedAt(LocalDateTime.now())
                .build();

        return returnRepository.save(returnExchange);
    }
}