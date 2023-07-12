package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

import javax.xml.crypto.KeySelector;

@Entity
@Table(name = "laptops")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Laptop {
    @Id
    @GeneratedValue(generator = "laptop_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "laptop_gen",sequenceName = "laptop_seq",allocationSize = 1)
    private Long id;
    private Processor processor;
    private Purpose purpose;
    private int videoMemory;
    private double screenSize;
}
