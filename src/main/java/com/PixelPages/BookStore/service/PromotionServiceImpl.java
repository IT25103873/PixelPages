package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.PromotionRequestDTO;
import com.PixelPages.BookStore.dto.PromotionResponseDTO;
import com.PixelPages.BookStore.entity.Promotion;
import com.PixelPages.BookStore.exception.PromotionNotFoundException;
import com.PixelPages.BookStore.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    private static final String PREFIX = "PROMO";

    private String generatePromoId() {
        String lastId = promotionRepository.findLastPromoId().orElse(null);
        int nextNumber = 1;
        if (lastId != null) {
            String numberPart = lastId.replace(PREFIX, "");
            nextNumber = Integer.parseInt(numberPart) + 1;
        }
        return PREFIX + String.format("%03d", nextNumber);
    }

    @Override
    public PromotionResponseDTO createPromotion(PromotionRequestDTO requestDTO) {
        Promotion promotion = new Promotion();
        promotion.setPromoId(generatePromoId());
        promotion.setName(requestDTO.getName());
        promotion.setDiscountPct(requestDTO.getDiscountPct());
        promotion.setCategoryId(requestDTO.getCategoryId());
        promotion.setStartDate(requestDTO.getStartDate());
        promotion.setEndDate(requestDTO.getEndDate());
        promotion.setActive(true);

        Promotion saved = promotionRepository.save(promotion);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<PromotionResponseDTO> getAllPromotions() {
        return promotionRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PromotionResponseDTO getPromotionById(String promoId) {
        Promotion promotion = promotionRepository.findById(promoId)
                .orElseThrow(() -> new PromotionNotFoundException("Promotion not found: " + promoId));
        return mapToResponseDTO(promotion);
    }

    @Override
    public PromotionResponseDTO updatePromotion(String promoId, PromotionRequestDTO requestDTO) {
        Promotion promotion = promotionRepository.findById(promoId)
                .orElseThrow(() -> new PromotionNotFoundException("Promotion not found: " + promoId));

        promotion.setName(requestDTO.getName());
        promotion.setDiscountPct(requestDTO.getDiscountPct());
        promotion.setCategoryId(requestDTO.getCategoryId());
        promotion.setStartDate(requestDTO.getStartDate());
        promotion.setEndDate(requestDTO.getEndDate());

        Promotion updated = promotionRepository.save(promotion);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deletePromotion(String promoId) {
        Promotion promotion = promotionRepository.findById(promoId)
                .orElseThrow(() -> new PromotionNotFoundException("Promotion not found: " + promoId));
        promotionRepository.delete(promotion);
    }

    private PromotionResponseDTO mapToResponseDTO(Promotion promotion) {
        return new PromotionResponseDTO(
                promotion.getPromoId(),
                promotion.getName(),
                promotion.getDiscountPct(),
                promotion.getCategoryId(),
                promotion.getStartDate(),
                promotion.getEndDate(),
                promotion.isActive()
        );
    }
}