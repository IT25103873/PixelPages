package repository;

import entity.ReturnExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnExchangeRepository extends JpaRepository<ReturnExchange, Long> {
    List<ReturnExchange> findByStatus(String status);
    List<ReturnExchange> findByOrderItemOrderOrderId(Long orderId);
}
