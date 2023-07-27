package peaksoft.house.gadgetariumb9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.house.gadgetariumb9.models.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
