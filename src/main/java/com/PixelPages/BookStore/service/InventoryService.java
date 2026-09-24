package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.InventoryRequestDTO;
import com.PixelPages.BookStore.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {
    InventoryResponseDTO getInventoryByBookId(Integer bookId);
    List<InventoryResponseDTO> getAllInventory();
    InventoryResponseDTO updateInventory(Integer bookId, InventoryRequestDTO dto);
    List<InventoryResponseDTO> getLowStockItems();
}