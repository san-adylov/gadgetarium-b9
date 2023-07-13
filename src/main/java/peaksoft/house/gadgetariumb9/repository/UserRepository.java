package peaksoft.house.gadgetariumb9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}