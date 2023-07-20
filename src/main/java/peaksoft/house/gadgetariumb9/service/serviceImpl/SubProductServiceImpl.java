package peaksoft.house.gadgetariumb9.service.serviceImpl;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.PhoneCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductCatalogResponse;
import peaksoft.house.gadgetariumb9.repository.SubProductRepository;
import peaksoft.house.gadgetariumb9.service.SubProductService;

@Service
@Transactional
@RequiredArgsConstructor
public class SubProductServiceImpl implements SubProductService {

  private final SubProductRepository subProductRepository;
  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<SubProductCatalogResponse> getSubProductCatalogs(
      SubProductCatalogRequest subProductCatalogRequest) {
//    if (subProductCatalogRequest.getGadgetType().equalsIgnoreCase("SmartPhone") ||
//        subProductCatalogRequest.getGadgetType().equalsIgnoreCase("Tablet")) {
      return getPhoneFilter(new PhoneCatalogRequest(
          subProductCatalogRequest.getBrandIds(),
          subProductCatalogRequest.getPriceStart(),
          subProductCatalogRequest.getPriceEnd(),
          subProductCatalogRequest.getCodeColor(),
          subProductCatalogRequest.getRom(),
          subProductCatalogRequest.getRam(),
          subProductCatalogRequest.getNovelties(),
          subProductCatalogRequest.getByShare(),
          subProductCatalogRequest.getRecommended(),
          subProductCatalogRequest.getByPriceIncrease(),
          subProductCatalogRequest.getByDecreasingPrice(),
          subProductCatalogRequest.getSim(),
          subProductCatalogRequest.getBatteryCapacity(),
          subProductCatalogRequest.getScreenSize(),
          subProductCatalogRequest.getScreenDiagonal(),
          subProductCatalogRequest.getScreenResolution()
      ));
//    }
//    return null;
  }

  private List<SubProductCatalogResponse> getPhoneFilter(PhoneCatalogRequest phoneCatalogRequest) {
    String sql = """
        SELECT s.id, d.sale, spi.images, s.quantity, s.name, s.price
        FROM sub_products s
                 JOIN sub_product_images spi on s.id = spi.sub_product_id
                 JOIN discounts d on s.id = d.sub_product_id
                 JOIN phones p on s.id = p.sub_product_id
                 JOIN products p2 on s.product_id = p2.id
                 JOIN brands b on p2.brand_id = b.id
                 JOIN sub_categories sc on p2.sub_category_id = sc.id
                 JOIN categories c on sc.category_id = c.id
        WHERE (:brandIds IS NULL OR b.id IN (:brandIds))
          AND (:priceStart IS NULL OR s.price >= :priceStart)
          AND (:priceEnd IS NULL OR s.price <= :priceEnd)
          AND (:colorCode IS NULL OR s.code_color IN (:colorCode))
          AND (:rom IS NULL OR s.rom IN (:rom))
          AND (:ram IS NULL OR s.ram IN (:ram))
          AND (:sim IS NULL OR p.sim IN (:sim))
          AND (:batteryCapacity IS NULL OR p.battery_capacity IN (:batteryCapacity))
          AND (:screenSize IS NULL OR p.screen_size IN (:screenSize))
          AND (:screenDiagonal IS NULL OR p.diagonal_screen IN (:screenDiagonal))
          AND (:screenResolution IS NULL OR s.screen_resolution IN (:screenResolution))
        """;
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("brandIds", phoneCatalogRequest.getBrandIs());
    params.addValue("priceStart", phoneCatalogRequest.getPriceStart());
    params.addValue("priceEnd", phoneCatalogRequest.getPriceEnd());
    params.addValue("codeColor", phoneCatalogRequest.getCodeColor());
    params.addValue("rom", phoneCatalogRequest.getRom());
    params.addValue("ram", phoneCatalogRequest.getRam());
    params.addValue("sim", phoneCatalogRequest.getSim());
    params.addValue("batteryCapacities", phoneCatalogRequest.getBatteryCapacity());
    params.addValue("screenSizes", phoneCatalogRequest.getScreenSize());
    params.addValue("screenDiagonals", phoneCatalogRequest.getScreenDiagonal());
    params.addValue("screenResolutions", phoneCatalogRequest.getScreenResolution());

    return jdbcTemplate.query(sql,(rs, rowNum) -> new SubProductCatalogResponse(
        rs.getLong("id"),
        rs.getInt("sale"),
        rs.getString("images"),
        rs.getInt("quantity"),
        rs.getString("name"),
        rs.getBigDecimal("price")
    ) , params);
    }

  //    params.addValue("novelties", novelties);
//    params.addValue("byShare", byShare);
//    params.addValue("recommended", recommended);
//    params.addValue("byPriceIncrease", byPriceIncrease);
//    params.addValue("byDecreasingPrice", byDecreasingPrice);
}
