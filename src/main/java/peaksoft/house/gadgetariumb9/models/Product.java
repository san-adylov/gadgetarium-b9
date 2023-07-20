package peaksoft.house.gadgetariumb9.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.List;
import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "products")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(generator = "product_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_gen", sequenceName = "product_seq", allocationSize = 1, initialValue = 6)
    private Long id;

    private String name;

    private ZonedDateTime dataOfIssue;

    private int guarantee;

    private ZonedDateTime createdAt;

    private String description;

    private String pdf;

    private String videoLink;

    @ManyToOne(
            cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private Brand brand;

    @ManyToOne(
            cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private SubCategory subCategory;

    @OneToMany(
            mappedBy = "product",
            cascade = {MERGE, DETACH, REFRESH, PERSIST, REMOVE})
    private List<SubProduct> subProducts;
}
