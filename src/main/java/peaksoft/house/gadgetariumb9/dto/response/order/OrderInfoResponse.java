package peaksoft.house.gadgetariumb9.dto.response.order;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderInfoResponse {

    private Long orderId;

    private int orderNumber;

    private String status;

    private String phoneNumber;

    private String address;

    private OrderProductResponse productResponseList;
}
