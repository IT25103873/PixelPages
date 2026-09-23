package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
