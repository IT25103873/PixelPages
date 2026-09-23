package controller;

import dto.DeliveryStatusUpdateRequest;
import dto.ReturnRequestDTO;
import entity.Delivery;
import entity.ReturnExchange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.DeliveryAndReturnService;

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