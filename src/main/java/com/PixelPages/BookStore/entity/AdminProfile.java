package com.PixelPages.BookStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AdminProfile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminProfile {

    @Id
    @Column(name = "admin_id", length = 10)
    private String adminId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userId;
}