package com.PixelPages.BookStore.controller;

import com.PixelPages.BookStore.dto.FeedbackRequestDTO;
import com.PixelPages.BookStore.dto.FeedbackResponseDTO;
import com.PixelPages.BookStore.dto.FeedbackStatusUpdateDTO;
import com.PixelPages.BookStore.service.FeedbackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackServiceImpl feedbackService;

    @PostMapping
    public ResponseEntity<FeedbackResponseDTO> createFeedback(@RequestBody FeedbackRequestDTO requestDTO) {
        return new ResponseEntity<>(feedbackService.createFeedback(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponseDTO>> getAllFeedback() {
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbackByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(feedbackService.getFeedbackByUserId(userId));
    }

    // ?type=FEEDBACK or ?type=REVIEW
    @GetMapping("/type/{type}")
    public ResponseEntity<List<FeedbackResponseDTO>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(feedbackService.getByType(type.toUpperCase()));
    }

    @GetMapping("/book/{bookId}/reviews")
    public ResponseEntity<List<FeedbackResponseDTO>> getReviewsByBookId(@PathVariable Integer bookId) {
        return ResponseEntity.ok(feedbackService.getReviewsByBookId(bookId));
    }

    @PatchMapping("/{feedbackId}/status")
    public ResponseEntity<FeedbackResponseDTO> updateFeedbackStatus(@PathVariable String feedbackId, @RequestBody FeedbackStatusUpdateDTO statusDTO) {
        return ResponseEntity.ok(feedbackService.updateFeedbackStatus(feedbackId, statusDTO));
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable String feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok("Feedback deleted successfully.");
    }
}