package com.PixelPages.BookStore.controller;

import com.PixelPages.BookStore.dto.SupplierRequest;
import com.PixelPages.BookStore.dto.SupplierResponse;
import com.PixelPages.BookStore.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponse> createSupplier(@RequestBody SupplierRequest request) {
        SupplierResponse response = supplierService.createSupplier(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }
}
