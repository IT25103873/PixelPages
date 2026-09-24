package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.CategoryRequestDTO;
import com.PixelPages.BookStore.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO dto);
    CategoryResponseDTO getCategoryById(Integer id);
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO updateCategory(Integer id, CategoryRequestDTO dto);
    void deleteCategory(Integer id);
}