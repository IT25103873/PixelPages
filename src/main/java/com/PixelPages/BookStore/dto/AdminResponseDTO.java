package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {
    private String adminId;
    private Integer userId;
    private String fullName;
    private String email;
    private String phone;
    private boolean isActive;
    private LocalDateTime createdAt;
}