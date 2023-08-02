package peaksoft.house.gadgetariumb9.dto.response.order;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseAdmin {

    private Long id;

    private String fullName;

    private int orderNumber;

    private int quantity;

    private BigDecimal totalPrice;

    private String typeDelivery;

    private String status;


}
