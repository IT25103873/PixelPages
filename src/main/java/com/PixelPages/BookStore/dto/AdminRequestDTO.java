package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {
    private String fullName;
    private String email;
    private String password;
    private String phone;
}