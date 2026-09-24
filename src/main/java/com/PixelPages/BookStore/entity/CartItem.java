package com.PixelPages.BookStore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CartItems", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_Cart_Book", columnNames = {"cart_id", "book_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private ShoppingCart cart;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
