package repository;

import entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartCartId(Long cartId);
    Optional<CartItem> findByCartCartIdAndBookId(Long cartId, Long bookId);
    void deleteByCartCartId(Long cartId);
}
