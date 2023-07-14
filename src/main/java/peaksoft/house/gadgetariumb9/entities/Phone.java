package peaksoft.house.gadgetariumb9.entities;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.PERSIST;

@Entity
@Table(name = "phones")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {

    @Id
    @GeneratedValue(generator = "phone_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "phone_gen", sequenceName = "phone_seq", allocationSize = 1, initialValue = 6)
    private Long id;

    private int sim;

    private String diagonalScreen;

    private String batteryCapacity;

    private double screenSize;

    @OneToOne(
            cascade = {MERGE, DETACH, REFRESH, PERSIST})
    private SubProduct subProduct;
}
