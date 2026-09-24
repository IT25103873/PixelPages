package com.PixelPages.BookStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "CustomerFeedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @Column(name = "feedback_id", length = 10)
    private String feedbackId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "book_id")
    private Integer bookId;   // REVIEW type එකට විතරයි, FEEDBACK එකට null

    @Column(nullable = false, length = 20)
    private String type;      // FEEDBACK or REVIEW

    @Column(length = 150)
    private String subject;   // FEEDBACK type එකට විතරයි

    @Column(nullable = false, length = 1000)
    private String message;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}