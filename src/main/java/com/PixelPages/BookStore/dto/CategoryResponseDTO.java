package com.PixelPages.BookStore.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoryResponseDTO {
    private Integer id;
    private String name;
    private String description;
}