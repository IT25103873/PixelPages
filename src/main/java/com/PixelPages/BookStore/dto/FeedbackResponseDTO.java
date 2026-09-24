package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseDTO {
    private String feedbackId;
    private Integer userId;
    private Integer bookId;
    private String type;
    private String subject;
    private String message;
    private int rating;
    private String status;
    private LocalDateTime createdAt;
}