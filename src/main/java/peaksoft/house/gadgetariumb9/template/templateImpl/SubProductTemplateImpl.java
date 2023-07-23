package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductCatalogResponse;
import peaksoft.house.gadgetariumb9.template.SubProductTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class SubProductTemplateImpl implements SubProductTemplate {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<SubProductCatalogResponse> getProductFilter(
      SubProductCatalogRequest subProductCatalogRequest) {
    String sql = """
            SELECT s.id, d.sale, (SELECT spi.images FROM sub_product_images spi WHERE spi.sub_product_id = s.id LIMIT 1) AS image, s.quantity, p2.name, s.price
            FROM sub_products s
                     FULL JOIN discounts d ON s.id = d.sub_product_id
                     FULL JOIN phones p ON s.id = p.sub_product_id
                     FULl JOIN laptops l ON s.id = l.sub_product_id
                     FULL JOIN smart_watches sw ON s.id = sw.sub_product_id
                     FULL JOIN products p2 ON s.product_id = p2.id
                     FULL JOIN brands b ON p2.brand_id = b.id
                     FULL JOIN sub_categories sc ON p2.sub_category_id = sc.id
                     FULL JOIN categories c ON sc.category_id = c.id
            WHERE c.title = ?
        """;
    List<Object> params = new ArrayList<>();
    params.add(subProductCatalogRequest.getGadgetType());
    if (subProductCatalogRequest.getBrandIds() != null) {
      sql += "OR b.id = ANY (?)";
      params.add(subProductCatalogRequest.getBrandIds().toArray(new Long[0]));
    }
    if (subProductCatalogRequest.getPriceStart().compareTo(BigDecimal.ZERO) != 0) {
      sql += " OR (s.price >= ?)";
      params.add(subProductCatalogRequest.getPriceStart());
    }
    if (subProductCatalogRequest.getPriceEnd().compareTo(BigDecimal.ZERO) != 0) {
      sql += " OR (s.price <= ?)";
      params.add(subProductCatalogRequest.getPriceEnd());
    }
    if (!subProductCatalogRequest.getCodeColor().get(0).equalsIgnoreCase("string")) {
      sql += " OR (s.code_color = ANY (?))";
      params.add(subProductCatalogRequest.getCodeColor().toArray(new String[0]));
    }
    if (subProductCatalogRequest.getRam().get(0) > 0) {
      sql += " OR (s.ram = ANY (?))";
      params.add(subProductCatalogRequest.getRam().toArray(new Integer[0]));
    }
    if (subProductCatalogRequest.getRom().get(0) > 0) {
      sql += " OR (s.rom = ANY (?))";
      params.add(subProductCatalogRequest.getRom().toArray(new Integer[0]));
    }
    if (subProductCatalogRequest.getSim().get(0) > 0) {
      sql += "OR (p.sim = ?)";
      params.add(subProductCatalogRequest.getSim());
    }
    if (!subProductCatalogRequest.getBatteryCapacity().get(0).equalsIgnoreCase("string")) {
      sql += "OR (p.battery_capacity = ANY (?))";
      params.add(subProductCatalogRequest.getBatteryCapacity().toArray(new String[0]));
    }
    if (subProductCatalogRequest.getScreenSize().get(0) > 0) {
      sql += "OR (p.screen_size = ANY (?))";
      params.add(subProductCatalogRequest.getScreenSize().toArray(new Double[0]));
    }
    if (!subProductCatalogRequest.getScreenResolution().get(0).equalsIgnoreCase("string")) {
      sql += "OR (s.screen_resolution = ANY (?))";
      params.add(subProductCatalogRequest.getScreenResolution().toArray(new String[0]));
    }
    if (subProductCatalogRequest.getProcessors().get(0).equalsIgnoreCase("string")) {
      sql += " OR (l.processor = ANY (?))";
      params.add(subProductCatalogRequest.getProcessors().toArray(new String[0]));
    }
    if (subProductCatalogRequest.getPurposes().get(0).equalsIgnoreCase("string")) {
      sql += " OR (l.purpose = ANY (?))";
      params.add(subProductCatalogRequest.getPurposes().toArray(new String[0]));
    }
    if (subProductCatalogRequest.getVideoMemory() != null
        && subProductCatalogRequest.getVideoMemory().get(0) > 0) {
      sql += "OR (l.video_memory = ANY(?))";
      params.add(subProductCatalogRequest.getVideoMemory().toArray(new Integer[0]));
    }
    if (subProductCatalogRequest.getHousingMaterials().get(0).equalsIgnoreCase("string")) {
      sql += "OR (sw.housing_material = ANY (?))";
      params.add(subProductCatalogRequest.getHousingMaterials().toArray(new String[0]));
    }
    if (subProductCatalogRequest.getMaterialBracelets().get(0).equalsIgnoreCase("string")) {
      sql += "OR (sw.material_bracelet = ANY(?))";
      params.add(subProductCatalogRequest.getMaterialBracelets().toArray(new String[0]));
    }
    if (subProductCatalogRequest.getGenders().get(0).equalsIgnoreCase("string")) {
      sql += "OR (sw.gender = ANY(?))";
      params.add(subProductCatalogRequest.getGenders().toArray(new String[0]));
    }
    if (subProductCatalogRequest.getInterfaces().get(0).equalsIgnoreCase("string")) {
      sql += "OR (sw.an_interface = ANY(?))";
      params.add(subProductCatalogRequest.getInterfaces().toArray(new String[0]));
    }
    if (subProductCatalogRequest.getHullShapes().get(0).equalsIgnoreCase("string")) {
      sql += "OR (sw.hull_shape = ANY(?))";
      params.add(subProductCatalogRequest.getHullShapes().toArray(new String[0]));
    }

    return jdbcTemplate.query(sql, (rs, rowNum) -> new SubProductCatalogResponse(
        rs.getLong("id"),
        rs.getInt("sale"),
        rs.getString("image"),
        rs.getInt("quantity"),
        rs.getString("name"),
        rs.getBigDecimal("price")
    ), params.toArray());
  }
}
