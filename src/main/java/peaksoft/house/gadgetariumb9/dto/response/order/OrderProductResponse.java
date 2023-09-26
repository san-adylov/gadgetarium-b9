package peaksoft.house.gadgetariumb9.dto.response.order;

import java.util.List;
import lombok.*;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductResponse {

    private int orderNumber;

    private List<String> names;

    private int quantity;

    private BigDecimal allPrice;

    private int sale;

    private BigDecimal sumOfDiscount;

    private BigDecimal totalPrice;
}
