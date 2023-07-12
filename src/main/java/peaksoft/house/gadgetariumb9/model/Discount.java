package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "discounts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {
    @Id
    @GeneratedValue(generator = "discount_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "discount_gen",sequenceName = "discount_seq",allocationSize = 1)
    private Long id;
    private int sale;
    private ZonedDateTime startDate;
    private ZonedDateTime finishDate;
}
