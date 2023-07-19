package peaksoft.house.gadgetariumb9.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "sub_products")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubProduct {

    @Id
    @GeneratedValue(generator = "sub_product_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sub_product_gen", sequenceName = "sub_product_seq", allocationSize = 1, initialValue = 6)
    private Long id;

    private int ram;

    private String screenResolution;

    private int rom;

    private String additionalFeatures;

    private BigDecimal price;

    private int quantity;

    private String codeColor;

    @ElementCollection
    private List<String> images;

    private int articleNumber;

    @OneToOne(
            mappedBy = "subProduct",
            cascade = {MERGE, DETACH, REFRESH})
    private Discount discount;

    @OneToOne(
            mappedBy = "subProduct",
            cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private Laptop laptop;

    @OneToOne(
            mappedBy = "subProduct",
            cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private Phone phone;

    @OneToOne(
            mappedBy = "subProduct",
            cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private SmartWatch smartWatch;

    @ManyToMany(
            mappedBy = "subProducts",
            cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private List<Order> orders;

    @OneToMany(
            mappedBy = "subProduct",
            cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private List<Review> reviews;

    @ManyToMany(
            mappedBy = "subProducts",
            cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private List<Basket> baskets;

    @ManyToOne(
            cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private Product product;
}
