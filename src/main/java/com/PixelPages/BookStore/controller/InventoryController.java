package com.PixelPages.BookStore.controller;

import com.PixelPages.BookStore.dto.InventoryRequestDTO;
import com.PixelPages.BookStore.dto.InventoryResponseDTO;
import com.PixelPages.BookStore.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<InventoryResponseDTO> getInventoryByBookId(@PathVariable Integer bookId) {
        return ResponseEntity.ok(inventoryService.getInventoryByBookId(bookId));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(@PathVariable Integer bookId, @Valid @RequestBody InventoryRequestDTO dto) {
        return ResponseEntity.ok(inventoryService.updateInventory(bookId, dto));
    }
}