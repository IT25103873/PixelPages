package com.PixelPages.BookStore.service;

import com.PixelPages.BookStore.dto.CartItemRequest;
import com.PixelPages.BookStore.dto.CartResponse;
import com.PixelPages.BookStore.entity.CartItem;
import com.PixelPages.BookStore.entity.ShoppingCart;
import com.PixelPages.BookStore.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.PixelPages.BookStore.repository.CartItemRepository;
import com.PixelPages.BookStore.repository.ShoppingCartRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    private final ShoppingCartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(ShoppingCartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public ShoppingCart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(
                        ShoppingCart.builder()
                                .userId(userId)
                                .items(new ArrayList<>())
                                .build()
                ));
    }

    public CartResponse getCartByUserId(Long userId) {
        ShoppingCart cart = getOrCreateCart(userId);
        return mapToCartResponse(cart);
    }

    public CartResponse addItemToCart(Long userId, CartItemRequest request) {
        ShoppingCart cart = getOrCreateCart(userId);

        cartItemRepository.findByCartCartIdAndBookId(cart.getCartId(), request.getBookId())
                .ifPresentOrElse(
                        existingItem -> existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity()),
                        () -> {
                            CartItem newItem = CartItem.builder()
                                    .cart(cart)
                                    .bookId(request.getBookId())
                                    .quantity(request.getQuantity())
                                    .build();
                            cartItemRepository.save(newItem);
                        }
                );

        ShoppingCart updatedCart = cartRepository.findById(cart.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return mapToCartResponse(updatedCart);
    }

    public void removeCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new ResourceNotFoundException("Cart item not found with ID: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
    }

    public void clearCart(Long cartId) {
        cartItemRepository.deleteByCartCartId(cartId);
    }

    private CartResponse mapToCartResponse(ShoppingCart cart) {
        List<CartResponse.CartItemDTO> itemDTOs = cart.getItems().stream()
                .map(item -> CartResponse.CartItemDTO.builder()
                        .cartItemId(item.getCartItemId())
                        .bookId(item.getBookId())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return CartResponse.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUserId())
                .items(itemDTOs)
                .cartTotal(BigDecimal.ZERO)
                .build();
    }
}