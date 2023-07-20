package peaksoft.house.gadgetariumb9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}