package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.BookRequestDTO;
import com.PixelPages.BookStore.dto.BookResponseDTO;

import java.util.List;

public interface BookService {
    BookResponseDTO createBook(BookRequestDTO dto);
    BookResponseDTO getBookById(Integer id);
    List<BookResponseDTO> getAllBooks();
    BookResponseDTO updateBook(Integer id, BookRequestDTO dto);
    void deleteBook(Integer id);
}
