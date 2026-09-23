package com.PixelPages.BookStore.dto;

import com.PixelPages.BookStore.entity.BookFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    private String isbn;

    @NotNull(message = "Category is required")
    private Integer categoryId;

    private Integer supplierId;   // optional

    private Integer sellerId;     // optional

    @NotNull(message = "Format is required")
    private BookFormat format;

    @NotNull @DecimalMin(value = "0.0", inclusive = true, message = "Price must be positive")
    private BigDecimal price;

    private String description;

    private String imageUrl;
}