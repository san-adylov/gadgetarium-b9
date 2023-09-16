package peaksoft.house.gadgetariumb9.dto.response.basket;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponse {
    private Long subProductId;

    private String image;

    private String color;

    private String title;

    private int rating;

    private int numberOfReviews;

    private int quantity;

    private int articleNumber;

    private BigDecimal price;

    private int theNumberOfOrders;

    private boolean isFavorite;

    private boolean isComparison;

}
