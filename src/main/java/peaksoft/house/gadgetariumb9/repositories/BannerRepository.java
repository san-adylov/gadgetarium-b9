package peaksoft.house.gadgetariumb9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.house.gadgetariumb9.entities.Banner;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
}