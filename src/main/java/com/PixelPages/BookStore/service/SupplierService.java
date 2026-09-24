package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.SupplierRequest;
import com.PixelPages.BookStore.dto.SupplierResponse;

import java.util.List;

public interface SupplierService {
    SupplierResponse createSupplier(SupplierRequest request);
    List<SupplierResponse> getAllSuppliers();
}
