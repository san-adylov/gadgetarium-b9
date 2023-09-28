package peaksoft.house.gadgetariumb9.dto.response.order;

import java.math.BigDecimal;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdminOrderSearch {

  private Long orderId;

  private String userFullName;

  private int orderNumber;

  private int quantity;

  private BigDecimal totalPrice;

  private String typeDelivery;

  private String status;
}
