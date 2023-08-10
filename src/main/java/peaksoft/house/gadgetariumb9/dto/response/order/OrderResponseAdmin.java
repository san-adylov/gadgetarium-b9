package peaksoft.house.gadgetariumb9.dto.response.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseAdmin {

    private Long orderId;

    private String fullName;

    private int orderNumber;

    private int quantity;

    private BigDecimal totalPrice;

    private String typeDelivery;

    private String status;

    @Builder
    public OrderResponseAdmin(Long orderId, String fullName, int orderNumber, int quantity, BigDecimal totalPrice, String typeDelivery, String status) {
        this.orderId = orderId;
        this.fullName = fullName;
        this.orderNumber = orderNumber;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.typeDelivery = typeDelivery;
        this.status = status;
    }
}
