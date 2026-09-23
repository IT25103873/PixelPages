package com.PixelPages.BookStore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Suppliers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Integer id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(length = 200)
    private String address;
}
