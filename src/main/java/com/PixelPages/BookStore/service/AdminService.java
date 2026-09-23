package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.AdminRequestDTO;
import com.PixelPages.BookStore.dto.AdminResponseDTO;

import java.util.List;

public interface AdminService {
    AdminResponseDTO createAdmin(AdminRequestDTO requestDTO);
    List<AdminResponseDTO> getAllAdmins();
    AdminResponseDTO getAdminById(String adminId);
    AdminResponseDTO updateAdmin(String adminId, AdminRequestDTO requestDTO);
    void deleteAdmin(String adminId);
}