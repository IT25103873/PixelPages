package com.PixelPages.BookStore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO {
    private String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    // password purposely excluded - security
}