package com.PixelPages.BookStore.repository;

import com.PixelPages.BookStore.entity.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminProfile, String> {

    @Query("SELECT a.adminId FROM AdminProfile a ORDER BY a.adminId DESC")
    List<String> findAllAdminIdsSorted();

    Optional<AdminProfile> findByUserId(Integer userId);
}