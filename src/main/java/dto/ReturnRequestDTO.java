package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnRequestDTO {
    private Long orderItemId;
    private String requestType; // RETURN or EXCHANGE
    private String reason;
}