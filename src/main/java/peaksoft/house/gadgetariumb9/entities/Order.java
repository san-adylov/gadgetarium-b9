package peaksoft.house.gadgetariumb9.entities;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.house.gadgetariumb9.enums.Status;
import peaksoft.house.gadgetariumb9.enums.TypeDelivery;
import peaksoft.house.gadgetariumb9.enums.TypePayment;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

  @Id
  @GeneratedValue(generator = "order_gen", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "order_gen", sequenceName = "order_seq", allocationSize = 1)
  private Long id;

  private int quantity;

  private int totalDiscount;

  private BigDecimal totalPrice;

  private BigDecimal orderNumber;

  @Enumerated(EnumType.STRING)
  private TypeDelivery typeDelivery;

  @Enumerated(EnumType.STRING)
  private TypePayment typePayment;

  private ZonedDateTime dateOfOrder;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToMany(
      cascade = {MERGE, DETACH, REFRESH, PERSIST})
  private List<SubProduct> subProducts;

  @ManyToOne(
      cascade = {MERGE, DETACH, REFRESH, PERSIST})
  private User user;
}
