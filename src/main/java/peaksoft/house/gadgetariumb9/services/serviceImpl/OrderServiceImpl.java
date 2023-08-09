package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderInfoResponse;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderPaginationAdmin;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.enums.Status;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.Order;
import peaksoft.house.gadgetariumb9.repositories.OrderRepository;
import peaksoft.house.gadgetariumb9.services.OrderService;
import peaksoft.house.gadgetariumb9.template.OrderTemplate;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderTemplate orderTemplate;

    private final OrderRepository orderRepository;


    @Override
    public OrderPaginationAdmin getAllOrderAdmin(String status, int pageSize, int pageNumber, LocalDate startDate, LocalDate endDate) {
        return orderTemplate.getAllOrderAdmin(status, startDate, endDate, pageSize, pageNumber);
    }

    @Override
    public SimpleResponse updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.error(String.format("Order with id - %s is not found!", orderId));
            return new NotFoundException(String.format("Order with id - %s not found!", orderId));
        });
        Status newStatus;
        switch (status) {
            case "В ожидании" -> newStatus = Status.PENDING;
            case "Готов к выдаче" -> newStatus = Status.READY_FOR_DELIVERY;
            case "Получен" -> newStatus = Status.RECEIVED;
            case "Отменить" -> newStatus = Status.CANCEL;
            case "Курьер в пути" -> newStatus = Status.COURIER_ON_THE_WAY;
            case "Доставлен" -> newStatus = Status.DELIVERED;
            default -> {
                log.error("Статус не соответствует!");
                return SimpleResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Статус не соответствует!")
                        .build();
            }
        }
        order.setStatus(newStatus);

        orderRepository.save(order);
        log.error("successfully updated");
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("successfully updated")
                .build();
    }

    @Override
    public OrderInfoResponse getOrderInfo(Long orderId) {
        return orderTemplate.getOrderInfo(orderId);
    }

    @Override
    public SimpleResponse multiDeleteOrder(List<Long> orders) {
        for (Long orderId : orders) {
            Order order = orderRepository.findById(orderId).orElseThrow(() -> {
                log.error("Order with %s is not found" + orderId);
                return new NotFoundException("Order with %s is not found" + orderId);
            });
            order.getSubProducts().forEach(x -> x.getOrders().remove(order));

            orderRepository.delete(order);
        }

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("SubProducts with given IDs are deleted")
                .build();
    }

    @Override
    public SimpleResponse singleDelete(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order with %s is not found" + orderId);
            return new NotFoundException("Order with %s is not found" + orderId);
        });
        order.getSubProducts().forEach(x -> x.getOrders().remove(order));

        orderRepository.delete(order);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("SubProducts with given IDs are deleted")
                .build();
    }
}
