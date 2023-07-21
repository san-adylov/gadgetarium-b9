package peaksoft.house.gadgetariumb9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.models.Mailing;

public interface MailingRepository extends JpaRepository<Mailing, Long> {
}