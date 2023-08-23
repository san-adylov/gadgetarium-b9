package peaksoft.house.gadgetariumb9.dto.response.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OrderInfoResponse {

    private Long orderId;

    private int orderNumber;

    private String status;

    private String phoneNumber;

    private String address;

    private List<OrderProductResponse> productResponseList;

    @Builder
    public OrderInfoResponse(Long orderId, int orderNumber, String status, String phoneNumber, String address, List<OrderProductResponse> productResponseList) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.productResponseList = productResponseList;
    }
}
