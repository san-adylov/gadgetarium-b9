package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phones")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {
    @Id
    @GeneratedValue(generator = "phone_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "phone_gen",sequenceName = "phone_seq",allocationSize = 1)
    private Long id;
    private int sim;
    private String diagonalScreen;
    private String batteryCapacity;
    private double screenSize;
}
