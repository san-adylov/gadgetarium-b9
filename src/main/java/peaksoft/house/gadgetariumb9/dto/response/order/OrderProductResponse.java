package peaksoft.house.gadgetariumb9.dto.response.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class OrderProductResponse {

    private int orderNumber;

    private String name;

    private int quantity;

    private BigDecimal totalAmountOfOrder;

    private int sale;

    private BigDecimal sumOfDiscount;

    private BigDecimal total;

    @Builder
    public OrderProductResponse(int orderNumber, String name, int quantity, BigDecimal totalAmountOfOrder, int sale, BigDecimal sumOfDiscount, BigDecimal total) {
        this.orderNumber = orderNumber;
        this.name = name;
        this.quantity = quantity;
        this.totalAmountOfOrder = totalAmountOfOrder;
        this.sale = sale;
        this.sumOfDiscount = sumOfDiscount;
        this.total = total;
    }
}
