package peaksoft.house.gadgetariumb9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.house.gadgetariumb9.dto.response.category.SubCategoryResponse;
import peaksoft.house.gadgetariumb9.models.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("""
            SELECT NEW
            peaksoft.house.gadgetariumb9.dto.response.category.SubCategoryResponse
            (sc.id,sc.title)
            FROM SubCategory sc
            WHERE sc.category.id = ?1
            """)
    List<SubCategoryResponse> getAllSubCategory(Long categoryId);
}