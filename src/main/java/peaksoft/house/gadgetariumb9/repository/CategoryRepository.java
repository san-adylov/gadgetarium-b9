package peaksoft.house.gadgetariumb9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}