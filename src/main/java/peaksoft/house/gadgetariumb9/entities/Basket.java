package peaksoft.house.gadgetariumb9.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "baskets")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket {

    @Id
    @GeneratedValue(generator = "basket_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "basket_gen", sequenceName = "basket_seq", allocationSize = 1, initialValue = 6)
    private Long id;

    @ManyToMany(
            cascade = {MERGE, DETACH, REFRESH})
    private List<SubProduct> subProducts;

    @ManyToOne(
            cascade = {MERGE, DETACH, REFRESH})
    private User user;
}
