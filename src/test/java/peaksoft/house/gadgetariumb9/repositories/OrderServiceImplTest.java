package peaksoft.house.gadgetariumb9.repositories;

import jakarta.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.order.OrderUserRequest;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderUserResponse;
import peaksoft.house.gadgetariumb9.enums.Status;
import peaksoft.house.gadgetariumb9.enums.TypeDelivery;
import peaksoft.house.gadgetariumb9.enums.TypePayment;
import peaksoft.house.gadgetariumb9.models.Basket;
import peaksoft.house.gadgetariumb9.models.Order;
import peaksoft.house.gadgetariumb9.models.SubProduct;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.services.serviceImpl.OrderServiceImpl;
import peaksoft.house.gadgetariumb9.template.OrderTemplate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

class OrderServiceImplTest {

  private OrderServiceImpl orderService;

  private OrderRepository orderRepository;

  private BasketRepository basketRepository;

  private JwtService jwtService;

  private TemplateEngine templateEngine;

  private JavaMailSender emailSender;

  private OrderTemplate orderTemplate;

  private MimeMessage mimeMessage;

  @BeforeEach
  public void setup() {
    orderRepository = mock(OrderRepository.class);
    basketRepository = mock(BasketRepository.class);
    jwtService = mock(JwtService.class);
    templateEngine = mock(TemplateEngine.class);
    emailSender = mock(JavaMailSender.class);
    orderTemplate = mock(OrderTemplate.class);
    mimeMessage = mock(MimeMessage.class);

    orderService = new OrderServiceImpl(
        orderTemplate,
        orderRepository,
        emailSender,
        templateEngine,
        jwtService,
        basketRepository
    );
  }

  @Test
  void saveOrder() {

    OrderUserRequest request = OrderUserRequest.builder()
        .typeDelivery(TypeDelivery.DELIVERY)
        .firstName("Asan")
        .lastName("Asanov")
        .email("asan@example.com")
        .phoneNumber("0770060708")
        .address("Гражданская 119")
        .typePayment(TypePayment.CARD_ONLINE)
        .build();

    User user = User.builder()
        .id(1L)
        .firstName("Asan")
        .lastName("Asanov")
        .email("asan@example.com")
        .phoneNumber("0770060708")
        .address("Гражданская 119")
        .build();

    List<SubProduct> subProducts = new ArrayList<>();

    Order order = new Order();
    order.setId(3L);
    order.setQuantity(3);
    order.setTotalDiscount(3);
    order.setTotalPrice(BigDecimal.valueOf(25000));
    order.setTypeDelivery(TypeDelivery.DELIVERY);
    order.setTypePayment(TypePayment.CARD_ONLINE);
    order.setDateOfOrder(ZonedDateTime.now());
    order.setStatus(Status.IN_PROCESSING);
    order.setSubProducts(new ArrayList<>());
    order.setUser(user);
    int expectedArtValue = orderService.generate();
    order.setOrderNumber(expectedArtValue);

    Basket basket = new Basket();
    basket.setSubProducts(subProducts);

    when(jwtService.getAuthenticationUser()).thenReturn(user);
    when(basketRepository.findByUserId(user.getId())).thenReturn(basket);
    when(templateEngine.process(anyString(), any(IContext.class))).thenReturn("Email Text");
    when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
    doNothing().doThrow(new RuntimeException()).when(emailSender).send(any(MimeMessage.class));
    when(orderRepository.save(any(Order.class))).thenReturn(new Order());

    OrderUserResponse response = orderService.saveOrder(request);

    assertEquals(expectedArtValue, order.getOrderNumber());
    assertEquals(HttpStatus.OK, response.getStatus());
    assertTrue(response.getMessage().contains("Ваша заявка"));

    verify(orderRepository, times(1)).save(any(Order.class));
    verify(emailSender, times(1)).send(any(MimeMessage.class));
  }
}