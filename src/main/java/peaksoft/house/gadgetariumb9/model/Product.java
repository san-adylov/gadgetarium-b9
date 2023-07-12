package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "products")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(generator = "product_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_gen",sequenceName = "product_seq",allocationSize = 1)
    private Long id;
    private String name;
    private ZonedDateTime dataOfIssue;
    private int guarantee;
    private ZonedDateTime createdAt;
    private String description;
    private String pdf;
    private String videoLink;
    @ManyToOne
    private Brand brand;
}
