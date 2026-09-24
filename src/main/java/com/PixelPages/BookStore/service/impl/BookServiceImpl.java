package com.PixelPages.BookStore.service.impl;

import com.PixelPages.BookStore.dto.BookRequestDTO;
import com.PixelPages.BookStore.dto.BookResponseDTO;
import com.PixelPages.BookStore.entity.*;
import com.PixelPages.BookStore.exception.ResourceNotFoundException;
import com.PixelPages.BookStore.repository.*;
import com.PixelPages.BookStore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final SellerRepository sellerRepository;
    private final InventoryRepository inventoryRepository;   // <-- aluthෙන් add kala

    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Supplier supplier = null;
        if (dto.getSupplierId() != null) {
            supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + dto.getSupplierId()));
        }

        Seller seller = null;
        if (dto.getSellerId() != null) {
            seller = sellerRepository.findById(dto.getSellerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + dto.getSellerId()));
        }

        Book book = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .isbn(dto.getIsbn())
                .category(category)
                .supplier(supplier)
                .seller(seller)
                .format(dto.getFormat())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .isActive(true)
                .build();

        Book savedBook = bookRepository.save(book);

        // Aluthෙන් add kala parts — automatically Inventory record eka hadanawa
        Inventory inventory = Inventory.builder()
                .book(savedBook)
                .stockQuantity(0)
                .reorderLevel(5)
                .lastUpdated(LocalDateTime.now())
                .build();
        inventoryRepository.save(inventory);

        return mapToResponse(savedBook);
    }

    @Override
    public BookResponseDTO getBookById(Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return mapToResponse(book);
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO updateBook(Integer id, BookRequestDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Supplier supplier = null;
        if (dto.getSupplierId() != null) {
            supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + dto.getSupplierId()));
        }

        Seller seller = null;
        if (dto.getSellerId() != null) {
            seller = sellerRepository.findById(dto.getSellerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Seller not found with id: " + dto.getSellerId()));
        }

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setCategory(category);
        book.setSupplier(supplier);
        book.setSeller(seller);
        book.setFormat(dto.getFormat());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        book.setImageUrl(dto.getImageUrl());

        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
        // Inventory record eka database eke ON DELETE CASCADE eken automatically delete wenawa
    }

    private BookResponseDTO mapToResponse(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .categoryId(book.getCategory() != null ? book.getCategory().getId() : null)
                .categoryName(book.getCategory() != null ? book.getCategory().getName() : null)
                .supplierId(book.getSupplier() != null ? book.getSupplier().getId() : null)
                .supplierName(book.getSupplier() != null ? book.getSupplier().getName() : null)
                .sellerId(book.getSeller() != null ? book.getSeller().getId() : null)
                .format(book.getFormat())
                .price(book.getPrice())
                .description(book.getDescription())
                .imageUrl(book.getImageUrl())
                .isActive(book.getIsActive())
                .createdAt(book.getCreatedAt())
                .build();
    }
}