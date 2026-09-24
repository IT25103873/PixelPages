package com.PixelPages.BookStore.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SupplierResponseDTO {
    private Integer id;
    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
}