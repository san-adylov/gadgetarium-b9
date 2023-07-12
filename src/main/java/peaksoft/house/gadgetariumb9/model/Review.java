package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "reviews")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(generator = "review_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "review_gen",sequenceName = "review_seq",allocationSize = 1)
    private Long id;
    private ZonedDateTime dateCreatAd;
    private String comment;
    private String replyToComment;
    private int rating;
}
