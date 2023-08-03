package peaksoft.house.gadgetariumb9.dto.response.basket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponse {
    private Long subProductId;

    private String image;

    private String title;

    private int rating;

    private int numberOfReviews;

    private int quantity;

    private int articleNumber;

    private BigDecimal price;

    private int theNumberOfOrders;

}
