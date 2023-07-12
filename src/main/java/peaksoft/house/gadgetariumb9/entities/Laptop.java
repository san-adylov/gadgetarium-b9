package peaksoft.house.gadgetariumb9.entities;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.house.gadgetariumb9.enums.Processor;
import peaksoft.house.gadgetariumb9.enums.Purpose;
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
    @SequenceGenerator(name = "laptop_gen",sequenceName = "laptop_seq",allocationSize = 1, initialValue = 5)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Processor processor;
    @Enumerated(EnumType.STRING)
    private Purpose purpose;
    private int videoMemory;
    private double screenSize;
    @OneToOne(mappedBy = "laptop")
    private SubProduct subProduct;
}
