package peaksoft.house.gadgetariumb9.dto.response.product;

import java.math.BigDecimal;
import java.util.List;
import lombok.*;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUserAndAdminResponse {

  private Long productId;

  private Long subProductId;

  private String brandName;

  private String category;

  private String name;

  private int quantity;

  private List<String> colours;

  private List<String> images;

  private List<ReviewResponse> reviews;

  private BigDecimal price;

  private double rating;

  private int countOfReviews;

  private int articleNumber;

  private int discountOfProduct;

  private String screenResolution;

  private String color;

  private String dataOfIssue;

  private int rom;

  private int guarantee;

  private boolean isFavorite;

  private String description;

  private String videoLink;

  private boolean inBasket;
}