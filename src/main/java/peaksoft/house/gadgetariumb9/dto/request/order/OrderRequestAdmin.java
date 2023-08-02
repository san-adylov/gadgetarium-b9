package peaksoft.house.gadgetariumb9.dto.request.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequestAdmin {

    private String fullName;

    private int orderNumber;

    private int quantity;

    private BigDecimal totalPrice;

    private String typeDelivery;

    private String status;

    @Builder
    public OrderRequestAdmin(String fullName, int orderNumber, int quantity, BigDecimal totalPrice, String typeDelivery, String status) {
        this.fullName = fullName;
        this.orderNumber = orderNumber;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.typeDelivery = typeDelivery;
        this.status = status;
    }
}
