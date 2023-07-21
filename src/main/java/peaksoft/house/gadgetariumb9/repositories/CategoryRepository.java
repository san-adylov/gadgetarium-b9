package peaksoft.house.gadgetariumb9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.gadgetariumb9.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}