package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.FeedbackRequestDTO;
import com.PixelPages.BookStore.dto.FeedbackResponseDTO;
import com.PixelPages.BookStore.dto.FeedbackStatusUpdateDTO;
import com.PixelPages.BookStore.entity.Feedback;
import com.PixelPages.BookStore.exception.FeedbackNotFoundException;
import com.PixelPages.BookStore.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    private static final String PREFIX = "FDB";

    private String generateFeedbackId() {
        String lastId = feedbackRepository.findLastFeedbackId().orElse(null);
        int nextNumber = 1;
        if (lastId != null) {
            String numberPart = lastId.replace(PREFIX, "");
            nextNumber = Integer.parseInt(numberPart) + 1;
        }
        return PREFIX + String.format("%03d", nextNumber);
    }

    @Override
    public FeedbackResponseDTO createFeedback(FeedbackRequestDTO requestDTO) {
        if (requestDTO.getRating() < 1 || requestDTO.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (!"FEEDBACK".equals(requestDTO.getType()) && !"REVIEW".equals(requestDTO.getType())) {
            throw new IllegalArgumentException("Type must be either FEEDBACK or REVIEW");
        }
        if ("REVIEW".equals(requestDTO.getType()) && requestDTO.getBookId() == null) {
            throw new IllegalArgumentException("bookId is required for a REVIEW");
        }

        Feedback feedback = new Feedback();
        feedback.setFeedbackId(generateFeedbackId());
        feedback.setUserId(requestDTO.getUserId());
        feedback.setBookId(requestDTO.getBookId());
        feedback.setType(requestDTO.getType());
        feedback.setSubject(requestDTO.getSubject());
        feedback.setMessage(requestDTO.getMessage());
        feedback.setRating(requestDTO.getRating());
        feedback.setStatus("REVIEW".equals(requestDTO.getType()) ? "RESOLVED" : "OPEN");
        feedback.setCreatedAt(LocalDateTime.now());

        Feedback saved = feedbackRepository.save(feedback);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<FeedbackResponseDTO> getAllFeedback() {
        return feedbackRepository.findAll().stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackResponseDTO> getFeedbackByUserId(Integer userId) {
        return feedbackRepository.findByUserId(userId).stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackResponseDTO> getByType(String type) {
        return feedbackRepository.findByType(type).stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackResponseDTO> getReviewsByBookId(Integer bookId) {
        return feedbackRepository.findByBookIdAndType(bookId, "REVIEW").stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public FeedbackResponseDTO updateFeedback(String feedbackId, FeedbackRequestDTO requestDTO) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new FeedbackNotFoundException("Feedback not found: " + feedbackId));

        if (requestDTO.getRating() < 1 || requestDTO.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (!"FEEDBACK".equals(requestDTO.getType()) && !"REVIEW".equals(requestDTO.getType())) {
            throw new IllegalArgumentException("Type must be either FEEDBACK or REVIEW");
        }
        if ("REVIEW".equals(requestDTO.getType()) && requestDTO.getBookId() == null) {
            throw new IllegalArgumentException("bookId is required for a REVIEW");
        }

        feedback.setUserId(requestDTO.getUserId());
        feedback.setBookId(requestDTO.getBookId());
        feedback.setType(requestDTO.getType());
        feedback.setSubject(requestDTO.getSubject());
        feedback.setMessage(requestDTO.getMessage());
        feedback.setRating(requestDTO.getRating());

        Feedback updated = feedbackRepository.save(feedback);
        return mapToResponseDTO(updated);
    }

    @Override
    public FeedbackResponseDTO updateFeedbackStatus(String feedbackId, FeedbackStatusUpdateDTO statusDTO) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new FeedbackNotFoundException("Feedback not found: " + feedbackId));
        feedback.setStatus(statusDTO.getStatus());
        Feedback updated = feedbackRepository.save(feedback);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteFeedback(String feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new FeedbackNotFoundException("Feedback not found: " + feedbackId));
        feedbackRepository.delete(feedback);
    }

    private FeedbackResponseDTO mapToResponseDTO(Feedback feedback) {
        return new FeedbackResponseDTO(
                feedback.getFeedbackId(), feedback.getUserId(), feedback.getBookId(),
                feedback.getType(), feedback.getSubject(), feedback.getMessage(),
                feedback.getRating(), feedback.getStatus(), feedback.getCreatedAt()
        );
    }
}