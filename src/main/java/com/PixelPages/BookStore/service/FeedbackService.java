package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.FeedbackRequestDTO;
import com.PixelPages.BookStore.dto.FeedbackResponseDTO;
import com.PixelPages.BookStore.dto.FeedbackStatusUpdateDTO;

import java.util.List;

public interface FeedbackService {
    FeedbackResponseDTO createFeedback(FeedbackRequestDTO requestDTO);
    List<FeedbackResponseDTO> getAllFeedback();
    List<FeedbackResponseDTO> getFeedbackByUserId(Integer userId);
    List<FeedbackResponseDTO> getByType(String type);
    List<FeedbackResponseDTO> getReviewsByBookId(Integer bookId);
    FeedbackResponseDTO updateFeedback(String feedbackId, FeedbackRequestDTO requestDTO);
    FeedbackResponseDTO updateFeedbackStatus(String feedbackId, FeedbackStatusUpdateDTO statusDTO);
    void deleteFeedback(String feedbackId);
}