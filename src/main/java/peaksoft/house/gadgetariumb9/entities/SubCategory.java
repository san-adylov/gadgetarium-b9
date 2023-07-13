package peaksoft.house.gadgetariumb9.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "subCategories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategory {

  @Id
  @GeneratedValue(generator = "sub_category_gen", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "sub_category_gen", sequenceName = "sub_category_seq", allocationSize = 1)
  private Long id;
  private String title;

  @ManyToOne(
      cascade = {MERGE, DETACH, REFRESH, PERSIST})
  private Category category;

  @OneToMany(
      mappedBy = "subCategory",
      cascade = {MERGE, DETACH, REFRESH, PERSIST})
  private List<Product> products;
}
