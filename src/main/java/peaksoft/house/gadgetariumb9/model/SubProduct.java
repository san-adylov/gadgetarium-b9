package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "subProducts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubProduct {
    @Id
    @GeneratedValue(generator = "sub_product_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sub_product_gen",sequenceName = "sub_product_seq",allocationSize = 1)
    private Long id;
    private int ram;
    private String screenResolution;
    private int rom;
    private String additionalFeatures;
    private BigDecimal price;
    private int quantity;
    private String codeColor;
    @Lob
    private List<String>images;
    private int venderCode;
}
