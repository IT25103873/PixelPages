package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, String> {

    @Query("SELECT p.promoId FROM Promotion p ORDER BY p.promoId DESC")
    Optional<String> findLastPromoId();
}