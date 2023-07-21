package peaksoft.house.gadgetariumb9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.house.gadgetariumb9.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT COUNT(r) FROM Review r JOIN r.subProduct s WHERE s.id = :subProductId AND r.rating = :rating")
    int countReviewRating(@Param("subProductId") Long subProductId, @Param("rating") int rating);





}