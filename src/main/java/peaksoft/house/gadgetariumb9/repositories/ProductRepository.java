package peaksoft.house.gadgetariumb9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}