package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ReturnsExchanges")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_id")
    private Long returnId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @Column(name = "request_type", nullable = false, length = 20)
    private String requestType; // RETURN or EXCHANGE

    @Column(name = "reason", length = 300)
    private String reason;

    @Column(name = "status", nullable = false, length = 30)
    private String status; // REQUESTED, APPROVED, REJECTED, REFUNDED

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @PrePersist
    protected void onCreate() {
        if (this.requestedAt == null) {
            this.requestedAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "REQUESTED";
        }
        if (this.requestType == null) {
            this.requestType = "RETURN";
        }
    }
}