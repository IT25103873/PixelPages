package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackStatusUpdateDTO {
    private String status;   // OPEN / IN_PROGRESS / RESOLVED
}