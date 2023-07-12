package peaksoft.house.gadgetariumb9.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
@Entity
@Table(name = "mailings")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mailing {
    @Id
    @GeneratedValue(generator = "mailing_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "mailing_gen",sequenceName = "mailing_seq",allocationSize = 1, initialValue = 5)
    private Long id;
    private String title;
    private String description;
    private ZonedDateTime startDate;
    private ZonedDateTime finishDate;
    private String image;
}
