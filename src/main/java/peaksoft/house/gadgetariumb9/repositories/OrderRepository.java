package peaksoft.house.gadgetariumb9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}