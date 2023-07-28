package peaksoft.house.gadgetariumb9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.house.gadgetariumb9.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT COUNT(r) FROM Review r JOIN r.subProduct s WHERE s.id = :subProductId AND r.grade = :rating")
    int countReviewRating(@Param("subProductId") Long subProductId, @Param("rating") int rating);
}