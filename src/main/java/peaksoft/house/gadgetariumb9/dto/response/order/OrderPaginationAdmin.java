package peaksoft.house.gadgetariumb9.dto.response.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OrderPaginationAdmin {

    private int pageSize;

    private int pageNumber;

    private int quantity;

    private int IN_PROCESSING;

    private int READY_FOR_DELIVERY;

    private int DELIVERED;

    private List<OrderResponseAdmin> responseAdminList;

    @Builder
    public OrderPaginationAdmin(int pageSize, int pageNumber, int quantity, int IN_PROCESSING, int READY_FOR_DELIVERY, int DELIVERED, List<OrderResponseAdmin> responseAdminList) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.quantity = quantity;
        this.IN_PROCESSING = IN_PROCESSING;
        this.READY_FOR_DELIVERY = READY_FOR_DELIVERY;
        this.DELIVERED = DELIVERED;
        this.responseAdminList = responseAdminList;
    }
}
