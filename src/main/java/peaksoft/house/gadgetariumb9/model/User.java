package peaksoft.house.gadgetariumb9.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(generator = "user_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen",sequenceName = "user_seq",allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Role role;
    private String address;
    private boolean isSubscription;
    private String image;
    @Lob
    private List<Long>comparison;
    @Lob
    private List<Long>favorite;
}
