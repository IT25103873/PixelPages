package com.PixelPages.BookStore.service.impl;

import com.PixelPages.BookStore.dto.SupplierRequestDTO;
import com.PixelPages.BookStore.dto.SupplierResponseDTO;
import com.PixelPages.BookStore.entity.Supplier;
import com.PixelPages.BookStore.exception.ResourceNotFoundException;
import com.PixelPages.BookStore.repository.SupplierRepository;
import com.PixelPages.BookStore.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierResponseDTO createSupplier(SupplierRequestDTO dto) {
        Supplier supplier = Supplier.builder()
                .name(dto.getName())
                .contactPerson(dto.getContactPerson())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();

        return mapToResponse(supplierRepository.save(supplier));
    }

    @Override
    public SupplierResponseDTO getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return mapToResponse(supplier);
    }

    @Override
    public List<SupplierResponseDTO> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierResponseDTO updateSupplier(Integer id, SupplierRequestDTO dto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));

        supplier.setName(dto.getName());
        supplier.setContactPerson(dto.getContactPerson());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());

        return mapToResponse(supplierRepository.save(supplier));
    }

    @Override
    public void deleteSupplier(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        supplierRepository.delete(supplier);
    }

    private SupplierResponseDTO mapToResponse(Supplier supplier) {
        return SupplierResponseDTO.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .contactPerson(supplier.getContactPerson())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .address(supplier.getAddress())
                .build();
    }
}