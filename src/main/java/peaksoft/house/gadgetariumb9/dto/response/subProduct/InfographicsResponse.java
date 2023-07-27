package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class InfographicsResponse {

    private BigDecimal deliveredTotalPrice;

    private int deliveredQuantity;

    private BigDecimal waitingTotalPrice;

    private int waitingQuantity;

    private BigDecimal currentPeriod;

    private BigDecimal previousPeriod;

    public InfographicsResponse(BigDecimal deliveredTotalPrice, int deliveredQuantity,
                                BigDecimal waitingTotalPrice, int waitingQuantity, BigDecimal currentPeriod,
                                BigDecimal previousPeriod) {
        this.deliveredTotalPrice = deliveredTotalPrice;
        this.deliveredQuantity = deliveredQuantity;
        this.waitingTotalPrice = waitingTotalPrice;
        this.waitingQuantity = waitingQuantity;
        this.currentPeriod = currentPeriod;
        this.previousPeriod = previousPeriod;
    }
}