package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestDTO {
    private Integer userId;
    private Integer bookId;    // REVIEW request එකකට witharai denna - FEEDBACK ekakata null denna
    private String type;       // "FEEDBACK" or "REVIEW"
    private String subject;    // FEEDBACK ekakata witharai
    private String message;
    private int rating;
}