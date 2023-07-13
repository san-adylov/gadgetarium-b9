package peaksoft.house.gadgetariumb9.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

  @Id
  @GeneratedValue(generator = "category_gen", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "category_gen", sequenceName = "category_seq", allocationSize = 1)
  private Long id;
  private String title;
}
