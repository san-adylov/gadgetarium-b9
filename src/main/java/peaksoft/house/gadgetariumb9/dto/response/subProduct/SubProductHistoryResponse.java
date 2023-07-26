package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class SubProductHistoryResponse {

  private Long id;

  private String image;

  private String name;

  private double rating;

  private BigDecimal price;

  @Builder
  public SubProductHistoryResponse(Long id, String image, String name, double rating,
      BigDecimal price) {
    this.id = id;
    this.image = image;
    this.name = name;
    this.rating = rating;
    this.price = price;
  }
}
