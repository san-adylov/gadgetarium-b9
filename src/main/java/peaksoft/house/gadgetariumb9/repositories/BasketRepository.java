package peaksoft.house.gadgetariumb9.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.house.gadgetariumb9.models.Basket;
import java.util.List;
import peaksoft.house.gadgetariumb9.models.User;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> getBasketByUserId(Long userId);

    @Query("SELECT b FROM Basket b JOIN b.subProducts s WHERE s.id = ?1")
    List<Basket> getBasketBySubProductId(Long subProductId);

    @Query("SELECT b FROM Basket b WHERE b.user.id = :userId")
    Basket findByUserId(@Param("userId") Long userId);

    Optional<Basket> findByUser(User user);

}
