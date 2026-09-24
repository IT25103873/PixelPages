package com.PixelPages.BookStore.controller;

import com.PixelPages.BookStore.dto.AdminRequestDTO;
import com.PixelPages.BookStore.dto.AdminResponseDTO;
import com.PixelPages.BookStore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminResponseDTO> createAdmin(@RequestBody AdminRequestDTO requestDTO) {
        return new ResponseEntity<>(adminService.createAdmin(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AdminResponseDTO>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminResponseDTO> getAdminById(@PathVariable String adminId) {
        return ResponseEntity.ok(adminService.getAdminById(adminId));
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<AdminResponseDTO> updateAdmin(@PathVariable String adminId,
                                                        @RequestBody AdminRequestDTO requestDTO) {
        return ResponseEntity.ok(adminService.updateAdmin(adminId, requestDTO));
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable String adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.ok("Admin deleted successfully.");
    }
}