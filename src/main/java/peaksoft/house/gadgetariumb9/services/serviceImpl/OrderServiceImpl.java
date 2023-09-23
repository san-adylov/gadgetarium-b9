package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.order.OrderUserRequest;
import peaksoft.house.gadgetariumb9.dto.response.order.*;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.enums.Status;
import peaksoft.house.gadgetariumb9.enums.TypeDelivery;
import peaksoft.house.gadgetariumb9.exceptions.BadCredentialException;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.Basket;
import peaksoft.house.gadgetariumb9.models.Order;
import peaksoft.house.gadgetariumb9.models.SubProduct;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.BasketRepository;
import peaksoft.house.gadgetariumb9.repositories.OrderRepository;
import peaksoft.house.gadgetariumb9.repositories.SubProductRepository;
import peaksoft.house.gadgetariumb9.services.OrderService;
import peaksoft.house.gadgetariumb9.template.OrderTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final SubProductRepository subProductRepository;

    public static final String UTF_8 = "UTF-8";

    private final OrderTemplate orderTemplate;

    private final OrderRepository orderRepository;

    private final JavaMailSender emailSender;

    private final TemplateEngine templateEngine;

    private final JwtService jwtService;

    private final BasketRepository basketRepository;

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

    @Override
    public OrderUserResponse saveOrder(OrderUserRequest request) {

        User user = jwtService.getAuthenticationUser();
        List<SubProduct> products = new ArrayList<>();

        int totalQuantity = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalDiscount = 0;

        Basket basket = basketRepository.findByUserId(user.getId());
        if (basket != null) {
            List<SubProduct> subProducts = basket.getSubProducts();
            products.addAll(subProducts);

            for (SubProduct subProduct : subProducts) {
                int quantity = subProduct.getQuantity();
                totalQuantity += quantity;
                BigDecimal productCost = subProduct.getPrice().multiply(BigDecimal.valueOf(quantity));
                totalPrice = totalPrice.add(productCost);

                if (subProduct.getDiscount() != null) {
                    int discountPercentage = subProduct.getDiscount().getSale();
                    BigDecimal discountAmount = productCost.multiply(BigDecimal.valueOf(discountPercentage))
                            .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                            .setScale(0, RoundingMode.HALF_UP);

                    int discountAmountInt = discountAmount.intValue();
                    totalDiscount += discountAmountInt;
                }
            }
        }

        ZonedDateTime data = ZonedDateTime.now();
        LocalDate localDate = data.toLocalDate();

        Order order = new Order();
        order.setSubProducts(products);
        order.setDateOfOrder(data);
        order.setTotalPrice(totalPrice);
        order.setQuantity(totalQuantity);
        order.setTotalDiscount(totalDiscount);
        order.setTypeDelivery(request.getTypeDelivery());
        order.setTypePayment(request.getTypePayment());
        int orderNumber = generate();
        order.setOrderNumber(orderNumber);
        List<SubProduct> subProducts = new ArrayList<>();
        int quantity = 0;
        BigDecimal totalAmount = null;
        for (Long l : request.getSubProductIds()) {
            SubProduct subProduct = subProductRepository.findById(l).orElseThrow(() -> {
                log.error("Sub product id: %s not found".formatted(l));
                return new NotFoundException("Sub product id: %s not found".formatted(l));
            });
            subProducts.add(subProduct);
            if (totalAmount != null) {
            totalAmount.add(subProduct.getPrice());
            } else {
                totalAmount = new BigDecimal(subProduct.getPrice().precision());
            }
            quantity++;
        }
        order.setQuantity(quantity);
        order.setSubProducts(subProducts);
        Status status = Status.IN_PROCESSING;
        order.setStatus(status);
        TypeDelivery typeDelivery = order.getTypeDelivery();

//        updateUserInfo(user, request);

        try {
            Context context = new Context();
            context.setVariable("orderNumber", order.getOrderNumber());
            context.setVariable("dateOfOrder", localDate);
            context.setVariable("statusOrder", status.getValue());
            context.setVariable("user", request.getFirstName() + " " + request.getLastName());
            context.setVariable("phoneNumber", request.getPhoneNumber());
            context.setVariable("deliveryType", typeDelivery.getValue());
            String text = templateEngine.process("templates/order-email-template.html", context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8);
            helper.setPriority(1);
            helper.setSubject("Gadgetarium");
            helper.setFrom("shop.gadgetarium.kg@gmail.com");
            helper.setTo(request.getEmail());
            helper.setText(text, true);
            orderRepository.save(order);
            order.setUser(user);
            emailSender.send(message);
            return OrderUserResponse.builder()
                    .status(HttpStatus.OK)
                    .orderNumber(order.getOrderNumber())
                    .message(String.format("""
                                  Ваша заявка №%s от %s оформлена.\s
                            Вся актуальная информация о статусе исполнения\s
                                 заказа придет на указанный email:\s
                                                %s
                            """, order.getOrderNumber(), localDate, request.getEmail()))
                    .build();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new BadCredentialException("Message not sent!");
        }
    }

    public int generate() {
        return (int) (System.currentTimeMillis() % 1000000);
    }

    private void updateUserInfo(User user, OrderUserRequest request) {
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());
    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }

    public List<OrderHistoryResponse> getOrdersByUserId(Long userId) {
        return orderTemplate.getOrdersByUserId(userId);
    }

    @Override
    public OrderInfoByUserResponse getOrderByUser(Long orderId, Long userId) {
        orderRepository.findById(orderId).orElseThrow(() -> {
            log.error("Order with %s is not found" + orderId);
            return new NotFoundException("Order with %s is not found" + orderId);
        });
        return orderTemplate.getOrderByUser(orderId, userId);
    }
}
