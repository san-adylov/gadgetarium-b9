package peaksoft.house.gadgetariumb9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Boolean existsByName(String name);
}