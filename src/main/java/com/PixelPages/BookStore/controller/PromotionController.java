package com.PixelPages.BookStore.controller;

import com.PixelPages.BookStore.dto.PromotionRequestDTO;
import com.PixelPages.BookStore.dto.PromotionResponseDTO;
import com.PixelPages.BookStore.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping
    public ResponseEntity<PromotionResponseDTO> createPromotion(@RequestBody PromotionRequestDTO requestDTO) {
        return new ResponseEntity<>(promotionService.createPromotion(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PromotionResponseDTO>> getAllPromotions() {
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }

    @GetMapping("/{promoId}")
    public ResponseEntity<PromotionResponseDTO> getPromotionById(@PathVariable String promoId) {
        return ResponseEntity.ok(promotionService.getPromotionById(promoId));
    }

    @PutMapping("/{promoId}")
    public ResponseEntity<PromotionResponseDTO> updatePromotion(@PathVariable String promoId,
                                                                @RequestBody PromotionRequestDTO requestDTO) {
        return ResponseEntity.ok(promotionService.updatePromotion(promoId, requestDTO));
    }

    @DeleteMapping("/{promoId}")
    public ResponseEntity<String> deletePromotion(@PathVariable String promoId) {
        promotionService.deletePromotion(promoId);
        return ResponseEntity.ok("Promotion deleted successfully.");
    }
}