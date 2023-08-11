package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.response.order.OrderInfoResponse;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderPaginationAdmin;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderPaginationAdmin getAllOrderAdmin(String status, int pageSize, int pageNumber, LocalDate startDate, LocalDate endDate);

    SimpleResponse updateStatus(Long orderId, String status);

    OrderInfoResponse getOrderInfo(Long orderId);

    SimpleResponse multiDeleteOrder(List<Long> orders);

    SimpleResponse singleDelete(Long orderId);

}
