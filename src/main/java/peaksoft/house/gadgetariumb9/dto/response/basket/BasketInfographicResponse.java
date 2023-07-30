package peaksoft.house.gadgetariumb9.dto.response.basket;

import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@Builder
public class BasketInfographicResponse {

    private int quantitySubProducts;

    private double totalDiscount;

    private BigDecimal totalPrice;

    private BigDecimal toPay;

}
