package com.PixelPages.BookStore.service.impl;

import com.PixelPages.BookStore.dto.InventoryRequestDTO;
import com.PixelPages.BookStore.dto.InventoryResponseDTO;
import com.PixelPages.BookStore.entity.Inventory;
import com.PixelPages.BookStore.exception.ResourceNotFoundException;
import com.PixelPages.BookStore.repository.InventoryRepository;
import com.PixelPages.BookStore.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryResponseDTO getInventoryByBookId(Integer bookId) {
        Inventory inventory = inventoryRepository.findByBook_Id(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for book id: " + bookId));
        return mapToResponse(inventory);
    }

    @Override
    public List<InventoryResponseDTO> getAllInventory() {
        return inventoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponseDTO updateInventory(Integer bookId, InventoryRequestDTO dto) {
        Inventory inventory = inventoryRepository.findByBook_Id(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for book id: " + bookId));

        inventory.setStockQuantity(dto.getStockQuantity());
        inventory.setReorderLevel(dto.getReorderLevel());
        inventory.setLastUpdated(LocalDateTime.now());

        return mapToResponse(inventoryRepository.save(inventory));
    }

    private InventoryResponseDTO mapToResponse(Inventory inventory) {
        return InventoryResponseDTO.builder()
                .id(inventory.getId())
                .bookId(inventory.getBook().getId())
                .bookTitle(inventory.getBook().getTitle())
                .stockQuantity(inventory.getStockQuantity())
                .reorderLevel(inventory.getReorderLevel())
                .lastUpdated(inventory.getLastUpdated())
                .build();
    }
}