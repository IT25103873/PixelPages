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

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmailService emailService;

    private static final String PREFIX = "ADM";
    private static final String TEMP_PASSWORD_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private String generateAdminId() {
        List<String> ids = adminRepository.findAllAdminIdsSorted();
        String lastId = ids.isEmpty() ? null : ids.get(0);
        int nextNumber = 1;
        if (lastId != null) {
            String numberPart = lastId.replace(PREFIX, "");
            nextNumber = Integer.parseInt(numberPart) + 1;
        }
        return PREFIX + String.format("%03d", nextNumber);
    }

    private String generateTempPassword() {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(TEMP_PASSWORD_CHARS.charAt(RANDOM.nextInt(TEMP_PASSWORD_CHARS.length())));
        }
        return sb.toString();
    }

    @Override
    public AdminResponseDTO createAdmin(AdminRequestDTO requestDTO) {

        if (requestDTO.getFirstName() == null || requestDTO.getFirstName().isBlank()
                || requestDTO.getLastName() == null || requestDTO.getLastName().isBlank()) {
            throw new IllegalArgumentException("First name and last name are required");
        }

        // Check if email is already in use
        if (requestDTO.getEmail() != null && usersRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email '" + requestDTO.getEmail() + "' is already in use by another account.");
        }

        String tempPassword = generateTempPassword();

        Users user = new Users();
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setPasswordHash(tempPassword);
        user.setPhone(requestDTO.getPhone());
        user.setDateOfBirth(requestDTO.getDateOfBirth());
        user.setAddress(requestDTO.getAddress());
        user.setGender(requestDTO.getGender());
        user.setRole("ADMIN");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        Users savedUser = usersRepository.save(user);

        AdminProfile adminProfile = new AdminProfile();
        String adminId = generateAdminId();
        adminProfile.setAdminId(adminId);
        adminProfile.setUserId(savedUser.getUserId());

        AdminProfile savedProfile = adminRepository.save(adminProfile);

        emailService.sendAdminCredentials(savedUser.getEmail(), adminId, tempPassword);

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

        // Check if the new email is already used by a DIFFERENT user
        if (requestDTO.getEmail() != null && !requestDTO.getEmail().equalsIgnoreCase(user.getEmail())) {
            usersRepository.findByEmail(requestDTO.getEmail()).ifPresent(existing -> {
                if (!existing.getUserId().equals(user.getUserId())) {
                    throw new IllegalArgumentException("Email '" + requestDTO.getEmail() + "' is already in use by another account.");
                }
            });
        }

        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setPhone(requestDTO.getPhone());
        user.setDateOfBirth(requestDTO.getDateOfBirth());
        user.setAddress(requestDTO.getAddress());
        user.setGender(requestDTO.getGender());

        Users updatedUser = usersRepository.save(user);
        return mapToResponseDTO(profile, updatedUser);
    }

    @Override
    public void deleteAdmin(String adminId) {
        AdminProfile profile = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found: " + adminId));

        Integer userId = profile.getUserId();
        adminRepository.delete(profile);
        usersRepository.deleteById(userId);
    }

    private AdminResponseDTO mapToResponseDTO(AdminProfile profile, Users user) {
        return new AdminResponseDTO(
                profile.getAdminId(),
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getDateOfBirth(),
                user.getAddress(),
                user.getGender(),
                user.isActive(),
                user.getCreatedAt()
        );
    }
}