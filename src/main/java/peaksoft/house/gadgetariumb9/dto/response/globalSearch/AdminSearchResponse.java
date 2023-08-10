package peaksoft.house.gadgetariumb9.dto.response.globalSearch;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSearchResponse {

  private Long productId;

  private String images;

  private int articleNumber;

  private String name;

  private LocalDate dataOfIssue;

  private int quantity;

  private String priceAndSale;

  private BigDecimal currentPrice;
}
