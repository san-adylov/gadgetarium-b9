package peaksoft.house.gadgetariumb9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;
import peaksoft.house.gadgetariumb9.dto.response.compare.LatestComparison;
import peaksoft.house.gadgetariumb9.models.SubProduct;

import java.util.List;

@Repository
public interface SubProductRepository extends JpaRepository<SubProduct, Long> {


}
