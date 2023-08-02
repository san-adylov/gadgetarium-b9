package peaksoft.house.gadgetariumb9.dto.response.basket;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@Setter
public class BasketInfographicResponse {

    private int quantitySubProducts;

    private double totalDiscount;

    private BigDecimal totalPrice;

    private BigDecimal toPay;

    private List<BasketResponse> basketResponses;
}
