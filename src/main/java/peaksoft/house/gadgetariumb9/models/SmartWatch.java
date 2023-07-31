package peaksoft.house.gadgetariumb9.models;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.house.gadgetariumb9.enums.*;
import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "smart_watches")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartWatch {

    @Id
    @GeneratedValue(generator = "smart_watch_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "smart_watch_gen", sequenceName = "smart_watch_seq", allocationSize = 1, initialValue = 4)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Interface anInterface;

    @Enumerated(EnumType.STRING)
    private HullShape hullShape;

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
