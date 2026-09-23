package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {

    @Query("SELECT f.feedbackId FROM Feedback f ORDER BY f.feedbackId DESC")
    Optional<String> findLastFeedbackId();

    List<Feedback> findByUserId(Integer userId);

    List<Feedback> findByType(String type);          // "FEEDBACK" or "REVIEW" list veenama ganna

    List<Feedback> findByBookIdAndType(Integer bookId, String type);   // book ekaka reviews witharak ganna
}