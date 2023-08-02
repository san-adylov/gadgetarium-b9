package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderInfoResponse;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderPaginationAdmin;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.OrderService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = "Order API", description = "API for order CRUD management")
@PreAuthorize("hasAuthority('ADMIN')")

public class OrderApi {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "get all order" , description = "Displaying the total number of orders of users who bought the product")
    public OrderPaginationAdmin getAllOrder(@RequestParam(defaultValue = "В обработке",required = false) String status,
                                            @RequestParam(defaultValue = "6", required = false) int pageSize,
                                            @RequestParam(defaultValue = "1",required = false) int pageNumber,
                                            @RequestParam(required = false) LocalDate startDate,
                                            @RequestParam(required = false) LocalDate endDate) {
        return orderService.getAllOrderAdmin(status, pageSize, pageNumber, startDate, endDate);
    }

    @PostMapping("/{orderId}")
    @Operation(summary = "update status", description = "User Order Status Changes")
    public SimpleResponse updateStatus(@PathVariable Long orderId, @RequestParam String status) {
        return orderService.updateStatus(orderId, status);
    }

    @GetMapping("/get-order-by-id/{orderId}")
    @Operation(summary = "get-order-by-id", description = "Purchase information output")
    public OrderInfoResponse getOrderInfo(@PathVariable Long orderId) {
        return orderService.getOrderInfo(orderId);
    }

    @DeleteMapping("/single-delete/{orderId}")
    @Operation(summary = "single delete ", description = "Order deletion by administrator via id")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse singleDelete(@PathVariable Long orderId) {
        return orderService.singleDelete(orderId);
    }

    @DeleteMapping("/multi-delete")
    @Operation(summary = " multi delete", description = "Deleting orders selected by the administrator")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse multiDeleteOrder(@RequestBody List<Long> orderids) {
        return orderService.multiDeleteOrder(orderids);
    }
}
