package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductCatalogResponse;
import peaksoft.house.gadgetariumb9.entities.SubProduct;
import peaksoft.house.gadgetariumb9.service.SubProductService;

@Service
@Transactional
@RequiredArgsConstructor
public class SubProductTemplate implements SubProductService {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<SubProductCatalogResponse> getSubProductCatalogs(
      SubProductCatalogRequest subProductCatalogRequest) {

    return null;
  }

  private List<SubProductCatalogResponse> getProductByBrandIds(List<Long> brandIds){
    String sql = """
        SELECT s.id,d.sale,spi.images,s.quantity,p.name,s.price FROM sub_products s
        JOIN discounts d on s.id = d.sub_product_id
        JOIN sub_product_images spi on s.id = spi.sub_product_id
        JOIN products p on s.product_id = p.id
        WHERE brand_id IN (?)
        """;

    return  jdbcTemplate.query(sql, new Object[]{brandIds},
        (rs ,rowNum) -> new SubProductCatalogResponse(
            rs.getLong("id"),
            rs.getInt("sale"),
            rs.getString("images"),
            rs.getInt("quantity"),
            rs.getString("name"),
            rs.getBigDecimal("price")
    ));
  }
}
