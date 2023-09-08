package peaksoft.house.gadgetariumb9.dto.response.order;

import java.math.BigDecimal;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductsInfoResponse {

  private Long subProductId;

  private String productName;

  private double rating;

  private int reviewsCount;

  private BigDecimal price;

  private String image;
}
