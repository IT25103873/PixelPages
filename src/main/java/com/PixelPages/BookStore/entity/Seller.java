package com.PixelPages.BookStore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Sellers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Integer id;

    // Links to Users table - not mapping the full User entity here
    // since that's another team member's module
    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "business_name", length = 150)
    private String businessName;

    @Column(name = "bank_account_no", length = 50)
    private String bankAccountNo;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(name = "is_verified", nullable = false)
    @Builder.Default
    private Boolean isVerified = false;
}