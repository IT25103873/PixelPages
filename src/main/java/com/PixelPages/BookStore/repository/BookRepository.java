package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsByIsbn(String isbn);
}




