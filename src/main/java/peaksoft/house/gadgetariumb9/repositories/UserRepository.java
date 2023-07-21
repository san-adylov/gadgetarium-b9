package peaksoft.house.gadgetariumb9.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.models.User;

public interface UserRepository extends JpaRepository<User,Long> {

  Optional<User> getUserByEmail(String username);

  boolean existsByEmail(String username);

  Optional<User> getUserByResetToken(String token);

}