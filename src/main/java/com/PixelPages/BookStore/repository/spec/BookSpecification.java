package com.PixelPages.BookStore.repository.spec;

import com.PixelPages.BookStore.entity.Book;
import com.PixelPages.BookStore.entity.BookFormat;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class BookSpecification {

    public static Specification<Book> hasCategory(Integer categoryId) {
        return (root, query, cb) ->
                categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Book> hasFormat(BookFormat format) {
        return (root, query, cb) ->
                format == null ? null : cb.equal(root.get("format"), format);
    }

    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) ->
                (author == null || author.isBlank()) ? null :
                        cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%");
    }

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) ->
                (title == null || title.isBlank()) ? null :
                        cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> priceGreaterThanOrEqual(BigDecimal minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Book> priceLessThanOrEqual(BigDecimal maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Book> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("isActive"));
    }
}