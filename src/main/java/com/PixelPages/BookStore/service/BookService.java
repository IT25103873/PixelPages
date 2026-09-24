package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.BookRequestDTO;
import com.PixelPages.BookStore.dto.BookResponseDTO;
import com.PixelPages.BookStore.entity.BookFormat;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    BookResponseDTO createBook(BookRequestDTO dto);
    BookResponseDTO getBookById(Integer id);
    List<BookResponseDTO> getAllBooks();
    BookResponseDTO updateBook(Integer id, BookRequestDTO dto);
    void deleteBook(Integer id);

    // Aluthෙන් add kala - Advanced Filtering
    List<BookResponseDTO> filterBooks(Integer categoryId, BookFormat format, String author,
                                      String title, BigDecimal minPrice, BigDecimal maxPrice);
}