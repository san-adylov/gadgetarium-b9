package peaksoft.house.gadgetariumb9.dto.request.order;

import lombok.*;
import peaksoft.house.gadgetariumb9.enums.TypeDelivery;
import peaksoft.house.gadgetariumb9.enums.TypePayment;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUserRequest {

  private TypeDelivery typeDelivery;

  private String firstName;

  private String lastName;

  private String email;

  private String phoneNumber;

  private String address;

  private TypePayment typePayment;

  private List<Long> subProductIds;
}