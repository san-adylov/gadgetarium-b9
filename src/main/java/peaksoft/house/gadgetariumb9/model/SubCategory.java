package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subCategories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategory {
    @Id
    @GeneratedValue(generator = "sub_category_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sub_category_gen",sequenceName = "sub_category_seq",allocationSize = 1)
    private Long id;
    private String title;
    @ManyToOne
    private Category category;

}
