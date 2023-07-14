package peaksoft.house.gadgetariumb9.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> getUserByEmail(String username);

}
