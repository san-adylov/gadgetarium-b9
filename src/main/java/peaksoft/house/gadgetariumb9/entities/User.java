package peaksoft.house.gadgetariumb9.entities;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.house.gadgetariumb9.enums.Role;
import java.util.List;
import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.PERSIST;
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
    @SequenceGenerator(name = "user_gen",sequenceName = "user_seq",allocationSize = 1, initialValue = 5)
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
    @OneToMany(mappedBy = "user",cascade = {MERGE,DETACH,REFRESH,PERSIST,REMOVE})
    private List<Order>orders;
    @OneToMany(mappedBy = "user",cascade = {MERGE,DETACH,REFRESH,PERSIST})
    private List<Review>reviews;
    @OneToMany(mappedBy = "user",cascade = {MERGE,DETACH,REFRESH,PERSIST,REMOVE})
    private List<Basket>baskets;
}
