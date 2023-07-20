package peaksoft.house.gadgetariumb9.db.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.db.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {

  Optional<User> getUserByEmail(String username);

  boolean existsByEmail(String username);

  Optional<User> getUserByResetToken(String token);

}
