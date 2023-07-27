package peaksoft.house.gadgetariumb9.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "brands")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

    @Id
    @GeneratedValue(generator = "brand_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "brand_gen", sequenceName = "brand_seq", allocationSize = 1, initialValue = 6)
    private Long id;

    private String name;

    private String image;

    @OneToMany(
            mappedBy = "brand",
            cascade = {MERGE, DETACH, REFRESH, PERSIST, REMOVE})
    private List<Product> products;
}
