package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.SupplierRequestDTO;
import com.PixelPages.BookStore.dto.SupplierResponseDTO;

import java.util.List;

public interface SupplierService {
    SupplierResponseDTO createSupplier(SupplierRequestDTO dto);
    SupplierResponseDTO getSupplierById(Integer id);
    List<SupplierResponseDTO> getAllSuppliers();
    SupplierResponseDTO updateSupplier(Integer id, SupplierRequestDTO dto);
    void deleteSupplier(Integer id);
}