package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.AdminRequestDTO;
import com.PixelPages.BookStore.dto.AdminResponseDTO;
import com.PixelPages.BookStore.entity.AdminProfile;
import com.PixelPages.BookStore.entity.Users;
import com.PixelPages.BookStore.exception.AdminNotFoundException;
import com.PixelPages.BookStore.repository.AdminRepository;
import com.PixelPages.BookStore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UsersRepository usersRepository;

    private static final String PREFIX = "ADM";

    private String generateAdminId() {
        String lastId = adminRepository.findLastAdminId().orElse(null);
        int nextNumber = 1;
        if (lastId != null) {
            String numberPart = lastId.replace(PREFIX, "");
            nextNumber = Integer.parseInt(numberPart) + 1;
        }
        return PREFIX + String.format("%03d", nextNumber);
    }

    @Override
    public AdminResponseDTO createAdmin(AdminRequestDTO requestDTO) {

        // 1. Users table එකේ role='ADMIN' record එකක් හදනවා
        Users user = new Users();
        user.setFullName(requestDTO.getFullName());
        user.setEmail(requestDTO.getEmail());
        user.setPasswordHash(requestDTO.getPassword()); // TODO: BCrypt hash කරන්න production වලදී
        user.setPhone(requestDTO.getPhone());
        user.setRole("ADMIN");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        Users savedUser = usersRepository.save(user);

        // 2. AdminProfile record එක හදලා, Users record එකට link කරනවා
        AdminProfile adminProfile = new AdminProfile();
        adminProfile.setAdminId(generateAdminId());
        adminProfile.setUserId(savedUser.getUserId());

        AdminProfile savedProfile = adminRepository.save(adminProfile);

        return mapToResponseDTO(savedProfile, savedUser);
    }

    @Override
    public List<AdminResponseDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(profile -> {
                    Users user = usersRepository.findById(profile.getUserId())
                            .orElseThrow(() -> new AdminNotFoundException("Linked user not found for admin: " + profile.getAdminId()));
                    return mapToResponseDTO(profile, user);
                })
                .collect(Collectors.toList());
    }

    @Override
    public AdminResponseDTO getAdminById(String adminId) {
        AdminProfile profile = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found: " + adminId));
        Users user = usersRepository.findById(profile.getUserId())
                .orElseThrow(() -> new AdminNotFoundException("Linked user not found for admin: " + adminId));
        return mapToResponseDTO(profile, user);
    }

    @Override
    public AdminResponseDTO updateAdmin(String adminId, AdminRequestDTO requestDTO) {
        AdminProfile profile = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found: " + adminId));

        Users user = usersRepository.findById(profile.getUserId())
                .orElseThrow(() -> new AdminNotFoundException("Linked user not found for admin: " + adminId));

        user.setFullName(requestDTO.getFullName());
        user.setEmail(requestDTO.getEmail());
        user.setPhone(requestDTO.getPhone());
        if (requestDTO.getPassword() != null && !requestDTO.getPassword().isBlank()) {
            user.setPasswordHash(requestDTO.getPassword());
        }

        Users updatedUser = usersRepository.save(user);
        return mapToResponseDTO(profile, updatedUser);
    }

    @Override
    public void deleteAdmin(String adminId) {
        AdminProfile profile = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found: " + adminId));

        Integer userId = profile.getUserId();
        adminRepository.delete(profile);
        usersRepository.deleteById(userId); // Users record එකත් delete කරනවා (CASCADE එකෙන් AdminProfile auto-delete වුනත්, order එක safe කරන්න explicit කළා)
    }

    private AdminResponseDTO mapToResponseDTO(AdminProfile profile, Users user) {
        return new AdminResponseDTO(
                profile.getAdminId(),
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.isActive(),
                user.getCreatedAt()
        );
    }
}