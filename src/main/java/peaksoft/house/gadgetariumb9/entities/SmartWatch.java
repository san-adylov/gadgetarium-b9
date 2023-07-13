package peaksoft.house.gadgetariumb9.entities;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.house.gadgetariumb9.enums.*;
import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "smartWatches")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartWatch {

  @Id
  @GeneratedValue(generator = "smart_watch_gen", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "smart_watch_gen", sequenceName = "smart_watch_seq", allocationSize = 1)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Interface anInterface;

  @Enumerated(EnumType.STRING)
  private HullShape hUllShape;

  @Enumerated(EnumType.STRING)
  private MaterialBracelet materialBracelet;

  @Enumerated(EnumType.STRING)
  private HousingMaterial housingMaterial;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private boolean waterproof;

  private double displayDiscount;

  @OneToOne(
      cascade = {MERGE, DETACH, REFRESH, PERSIST})
  private SubProduct subProduct;
}