package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubProductCatalogResponse {

  private Long id;

  private int discount;

  private String image;

  private int quantity;

  private String name;

  private int rating;

  private BigDecimal price;

  @Builder
  public SubProductCatalogResponse(Long id, int discount, String image, int quantity, String name,
      int rating, BigDecimal price) {
    this.id = id;
    this.discount = discount;
    this.image = image;
    this.quantity = quantity;
    this.name = name;
    this.rating = rating;
    this.price = price;
  }
}
