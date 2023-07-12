package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(generator = "order_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "order_gen",sequenceName = "order_seq",allocationSize = 1)
    private Long id;
    private int quantity;
    private int totalDiscount;
    private BigDecimal totalPrice;
    private BigDecimal orderNumber;
    private TypeDelivery typeDelivery;
    private TypePayment typePayment;
    private ZonedDateTime dateOfOrder;
    private Status status;

}
