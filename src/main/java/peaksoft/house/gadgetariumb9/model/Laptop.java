package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.house.gadgetariumb9.enums.Processor;
import peaksoft.house.gadgetariumb9.enums.Purpose;

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
    @SequenceGenerator(name = "laptop_gen",sequenceName = "laptop_seq",allocationSize = 1, initialValue = 5)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private Processor processor;
    @Enumerated(value = EnumType.STRING)
    private Purpose purpose;
    private int videoMemory;
    private double screenSize;
    @OneToOne(mappedBy = "laptop")
    private SubProduct subProduct;
}
