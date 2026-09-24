package com.PixelPages.BookStore.service.impl;

import com.PixelPages.BookStore.dto.SupplierRequest;
import com.PixelPages.BookStore.dto.SupplierResponse;
import com.PixelPages.BookStore.entity.Supplier;
import com.PixelPages.BookStore.repository.SupplierRepository;
import com.PixelPages.BookStore.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public SupplierResponse createSupplier(SupplierRequest request) {
        Supplier supplier = Supplier.builder()
                .name(request.getName())
                .contactPerson(request.getContactPerson())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();

        Supplier savedSupplier = supplierRepository.save(supplier);

        return mapToResponse(savedSupplier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SupplierResponse mapToResponse(Supplier supplier) {
        return SupplierResponse.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .contactPerson(supplier.getContactPerson())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .address(supplier.getAddress())
                .build();
    }
}
