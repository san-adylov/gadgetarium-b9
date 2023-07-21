package peaksoft.house.gadgetariumb9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.models.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}