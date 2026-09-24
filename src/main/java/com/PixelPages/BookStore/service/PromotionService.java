package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.PromotionRequestDTO;
import com.PixelPages.BookStore.dto.PromotionResponseDTO;

import java.util.List;

public interface PromotionService {
    PromotionResponseDTO createPromotion(PromotionRequestDTO requestDTO);
    List<PromotionResponseDTO> getAllPromotions();
    PromotionResponseDTO getPromotionById(String promoId);
    PromotionResponseDTO updatePromotion(String promoId, PromotionRequestDTO requestDTO);
    void deletePromotion(String promoId);
}