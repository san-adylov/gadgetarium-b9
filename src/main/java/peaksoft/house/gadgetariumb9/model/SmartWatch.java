package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "smartWatches")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartWatch {
    @Id
    @GeneratedValue(generator = "smart_watch_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "smart_watch_gen",sequenceName = "smart_watch_seq",allocationSize = 1)
    private Long id;
    private Interface anInterface;
    private HUllShape hUllShape;
    private MaterialBacelet materialBacelet;
    private HousingMaterial housingMaterial;
    private Gender gender;
    private boolean waterproof;
    private double displayDiscount;
}
