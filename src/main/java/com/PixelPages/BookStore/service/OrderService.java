package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.CheckoutRequest;
import com.PixelPages.BookStore.dto.OrderResponse;
import com.PixelPages.BookStore.entity.Delivery;
import com.PixelPages.BookStore.entity.Order;
import com.PixelPages.BookStore.entity.OrderItem;
import com.PixelPages.BookStore.entity.ShoppingCart;
import com.PixelPages.BookStore.exception.InvalidOrderOperationException;
import com.PixelPages.BookStore.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PixelPages.BookStore.repository.DeliveryRepository;
import com.PixelPages.BookStore.repository.OrderRepository;
import com.PixelPages.BookStore.repository.ShoppingCartRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final ShoppingCartRepository cartRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository,
                        DeliveryRepository deliveryRepository,
                        ShoppingCartRepository cartRepository,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.deliveryRepository = deliveryRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    public OrderResponse checkout(CheckoutRequest request) {
        ShoppingCart cart = cartRepository.findByUserId(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart is empty or not found for user: " + request.getCustomerId()));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new InvalidOrderOperationException("Cannot checkout with an empty cart");
        }

        BigDecimal simulatedPrice = new BigDecimal("250.00");
        BigDecimal totalAmount = simulatedPrice.multiply(BigDecimal.valueOf(
                cart.getItems().stream().mapToInt(item -> item.getQuantity()).sum()
        ));

        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .addressId(request.getAddressId())
                .orderDate(LocalDateTime.now())
                .status("CONFIRMED")
                .totalAmount(totalAmount)
                .build();

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(ci -> OrderItem.builder()
                        .order(order)
                        .bookId(ci.getBookId())
                        .quantity(ci.getQuantity())
                        .unitPrice(simulatedPrice)
                        .build())
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        Delivery delivery = Delivery.builder()
                .order(order)
                .courierName("Certis Lanka")
                .trackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .status("PROCESSING")
                .build();

        order.setDelivery(delivery);

        Order savedOrder = orderRepository.save(order);

        // Explicitly clear items from the database-managed cart instance and persist
        cart.getItems().clear();
        cartRepository.save(cart);

        return mapToOrderResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
        return mapToOrderResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (!"PENDING".equalsIgnoreCase(order.getStatus()) && !"CONFIRMED".equalsIgnoreCase(order.getStatus())) {
            throw new InvalidOrderOperationException("Cannot cancel order with current status: " + order.getStatus());
        }

        order.setStatus("CANCELLED");
        if (order.getDelivery() != null) {
            order.getDelivery().setStatus("CANCELLED");
        }

        return mapToOrderResponse(orderRepository.save(order));
    }

    public OrderResponse mapToOrderResponse(Order order) {
        List<OrderResponse.OrderItemDTO> itemDTOs = order.getOrderItems() != null
                ? order.getOrderItems().stream()
                  .map(item -> OrderResponse.OrderItemDTO.builder()
                               .orderItemId(item.getOrderItemId())
                               .bookId(item.getBookId())
                               .quantity(item.getQuantity())
                               .unitPrice(item.getUnitPrice())
                               .build())
                  .collect(Collectors.toList())
                : List.of();

        OrderResponse.DeliveryDTO deliveryDTO = null;
        if (order.getDelivery() != null) {
            deliveryDTO = OrderResponse.DeliveryDTO.builder()
                    .deliveryId(order.getDelivery().getDeliveryId())
                    .courierName(order.getDelivery().getCourierName())
                    .trackingNumber(order.getDelivery().getTrackingNumber())
                    .status(order.getDelivery().getStatus())
                    .build();
        }

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .customerId(order.getCustomerId())
                .addressId(order.getAddressId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(itemDTOs)
                .delivery(deliveryDTO)
                .build();
    }
}