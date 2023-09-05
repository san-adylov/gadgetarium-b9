package peaksoft.house.gadgetariumb9.dto.response.order;

import java.time.LocalDate;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoByUserResponse {

  private Long orderId;

  private int orderNumber;

  private String status;

  private String client;

  private String firstName;

  private String lastName;

  private String address;

  private String phoneNumber;

  private String email;

  private LocalDate date;

  private String typePayment;

  private int totalDiscount;

  private int totalPrice;

  private List<OrderProductsInfoResponse> productsInfoResponses;
}

