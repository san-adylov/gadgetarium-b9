package peaksoft.house.gadgetariumb9.service.serviceImpl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.PhoneCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductCatalogResponse;
import peaksoft.house.gadgetariumb9.entities.SubProduct;
import peaksoft.house.gadgetariumb9.repository.SubProductRepository;
import peaksoft.house.gadgetariumb9.service.SubProductService;

@Service
@Transactional
@RequiredArgsConstructor
public class SubProductServiceImpl implements SubProductService {

  private final SubProductRepository subProductRepository;
  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<SubProductCatalogResponse> getSubProductCatalogs(SubProductCatalogRequest subProductCatalogRequest) {
   return getPhones(new PhoneCatalogRequest(
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

//    return phoneCatalogRequests.stream()
//        .map(this::getPhoneFilter)
//        .flatMap(List::stream)
//        .collect(Collectors.toList());
  }

//  private List<SubProductCatalogResponse> getPhoneFilter(PhoneCatalogRequest phoneCatalogRequest) {
//    String sql = """
//       SELECT s.id, d.sale, spi.images, s.quantity, p2.name, s.price
//       FROM sub_products s
//                FULL JOIN sub_product_images spi ON s.id = spi.sub_product_id
//                FULL JOIN discounts d ON s.id = d.sub_product_id
//                FULL JOIN phones p ON s.id = p.sub_product_id
//                FULL JOIN products p2 ON s.product_id = p2.id
//                FULL JOIN brands b ON p2.brand_id = b.id
//                FULL JOIN sub_categories sc ON p2.sub_category_id = sc.id
//                FULL JOIN categories c ON sc.category_id = c.id
//       WHERE (b.id IN (:brandIds))
//         AND (:priceStart IS NULL OR s.price >= :priceStart)
//         AND (:priceEnd IS NULL OR s.price <= :priceEnd)
//         AND (:colorCode IS NULL OR s.code_color IN (:colorCode))
//         AND (:ram IS NULL OR s.ram IN (:ram))
//         AND (:rom IS NULL OR s.rom IN (:rom))
//         AND (:sim IS NULL OR p.sim = :sim)
//         AND (:batteryCapacity IS NULL OR p.battery_capacity = :batteryCapacity)
//         AND ((:screenSizes IS NULL OR :screenSizes = []) OR
//              p.screen_size IN :screenSizes)
//         AND (:screenDiagonal IS NULL OR p.diagonal_screen = :screenDiagonal)
//         AND (:screenResolution IS NULL OR s.screen_resolution = :screenResolution)
//        """;
//    MapSqlParameterSource params = new MapSqlParameterSource();
//    params.addValue("brandIds", phoneCatalogRequest.getBrandIs());
//    params.addValue("priceStart", phoneCatalogRequest.getPriceStart());
//    params.addValue("priceEnd", phoneCatalogRequest.getPriceEnd());
//    params.addValue("codeColor", phoneCatalogRequest.getCodeColor());
//    params.addValue("ram", phoneCatalogRequest.getRam());
//    params.addValue("rom", phoneCatalogRequest.getRom());
//    params.addValue("sim", phoneCatalogRequest.getSim());
//    params.addValue("batteryCapacities", phoneCatalogRequest.getBatteryCapacity());
//    params.addValue("screenSizes", phoneCatalogRequest.getScreenSize());
//    params.addValue("screenDiagonals", phoneCatalogRequest.getScreenDiagonal());
//    params.addValue("screenResolutions", phoneCatalogRequest.getScreenResolution());
//
//    return jdbcTemplate.query(sql, (rs, rowNum) -> new SubProductCatalogResponse(
//        rs.getLong("id"),
//        rs.getInt("sale"),
//        rs.getString("images"),
//        rs.getInt("quantity"),
//        rs.getString("name"),
//        rs.getBigDecimal("price")
//    ), params);
//  }
  private List<SubProductCatalogResponse> getPhones(PhoneCatalogRequest phoneCatalogRequest){
    List<SubProduct> getSubProduct = subProductRepository.findAll();

  }

}
