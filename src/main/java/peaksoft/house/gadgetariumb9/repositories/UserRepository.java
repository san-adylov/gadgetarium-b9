package peaksoft.house.gadgetariumb9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.house.gadgetariumb9.dto.response.user.UserResponse;
import peaksoft.house.gadgetariumb9.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByEmail(String username);

    boolean existsByEmail(String username);

    boolean existsByEmailAndIdNot(String email, Long userId);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long userId);

    @Query("SELECT NEW peaksoft.house.gadgetariumb9.dto.response.user.UserResponse (u.firstName,u.lastName,u.email,u.phoneNumber,u.address,u.image) FROM User u WHERE u.id = ?1")
    Optional<UserResponse> getUserById(Long userId);

    User findByEmail (String email);
}
