package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "banners")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner {
    @Id
    @GeneratedValue(generator = "banner_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "banner_gen",sequenceName = "banner_seq",allocationSize = 1)
    private Long id;
    @Lob
    private List<String> images;


}
