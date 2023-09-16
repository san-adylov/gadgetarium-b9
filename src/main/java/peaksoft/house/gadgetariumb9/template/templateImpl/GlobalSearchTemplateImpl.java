package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.brand.BrandResponse;
import peaksoft.house.gadgetariumb9.dto.response.category.CategoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.AdminMainPagination;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.AdminSearchResponse;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductResponse;
import peaksoft.house.gadgetariumb9.exceptions.BadRequestException;
import peaksoft.house.gadgetariumb9.template.GlobalSearchTemplate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GlobalSearchTemplateImpl implements GlobalSearchTemplate {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public GlobalSearchResponse globalSearch(String keyword) {

    String sql = """
            
        SELECT b.id, b.name, b.image FROM brands b WHERE b.name ILIKE (concat('%' || ? || '%'))
                                                                                    """;
    List<BrandResponse> brandResponses = jdbcTemplate.query(
        sql,
        (rs, rowNum) -> BrandResponse.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .image(rs.getString("image"))
            .build(),
        keyword
    );

    String sql2 = """
         SELECT c.id, c.title
        FROM categories c
        WHERE c.title ILIKE (concat('%' || ? || '%'))
                   """;
    List<CategoryResponse> categoryResponses = jdbcTemplate.query(
        sql2,
        (rs, rowNum) -> CategoryResponse.builder()
            .categoryId(rs.getLong("id"))
            .title(rs.getString("title"))
            .build(),
        keyword
    );
    String sql3 = """
        SELECT p.name,
               b.name                as brandName,
               s.id                  as subProductId,
               s.ram,
               s.screen_resolution   as screenResolution,
               s.rom,
               s.additional_features as additionalFeatures,
               s.price,
               s.quantity,
               s.code_color,
               (SELECT spi.images
                FROM sub_product_images spi
                WHERE spi.sub_product_id = s.id
                LIMIT 1)             as image,
               s.article_number
        FROM sub_products s
                 JOIN products p on p.id = s.product_id
                 JOIN brands b on b.id = p.brand_id
        WHERE CAST(s.code_color AS TEXT) ILIKE (concat('%' || ? || '%'))
           OR CAST(s.price AS TEXT) ILIKE (concat('%' || ? || '%'))
           OR CAST(s.article_number AS TEXT) ILIKE (concat('%' || ? || '%'))                                                                       \s
         """;

    List<SubProductResponse> subProductResponses = jdbcTemplate.query(
        sql3,
        (rs, rowNum) -> SubProductResponse.builder()
            .name(rs.getString("name"))
            .brandName(rs.getString("brandName"))
            .subProductId(rs.getLong("subProductId"))
            .ram(rs.getInt("ram"))
            .screenResolution(rs.getString("screenResolution"))
            .rom(rs.getInt("rom"))
            .additionalFeatures(rs.getString("additionalFeatures"))
            .price(rs.getBigDecimal("price"))
            .quantity(rs.getString("quantity"))
            .codeColor(rs.getString("code_color"))
            .image(rs.getString("image"))
            .articleNumber(rs.getLong("article_number"))
            .build(),
        keyword, keyword, keyword
    );

    return GlobalSearchResponse.builder()
        .brandList(brandResponses)
        .categoryList(categoryResponses)
        .subProductResponses(subProductResponses)
        .build();
  }

  private int getQuantityCount(String query) {
    Integer quantityCount = jdbcTemplate.queryForObject(query, Integer.class);
    return quantityCount != null ? quantityCount : 0;
  }

  @Override
  public AdminMainPagination adminSearch(String keyword, String productType, LocalDate startDate,
      LocalDate endDate, int pageSize, int pageNumber) {

    String subProductQuery = "SELECT sum(s.quantity) from sub_products s";
    String orderQuery = "SELECT sum(o.quantity) from orders o";
    int subProductQuantityCount = getQuantityCount(subProductQuery);
    int orderQuantityCount = getQuantityCount(orderQuery);
    int difference = orderQuantityCount - subProductQuantityCount;
    String sql = "";

    if (productType != null) {
      if (productType.equalsIgnoreCase("Все товары")) {
        sql = """
            SELECT sp.id                                                          AS sub_product_id,
                   (SELECT spi.images
                    FROM sub_product_images spi
                    WHERE spi.sub_product_id = sp.id
                    LIMIT 1)                                                      AS image,
                   sp.article_number                                              AS article_number,
                   CONCAT(b.name, ' ', p.name)                                    AS name,
                   p.created_at                                                   AS date_of_creation,
                   sp.quantity                                                    AS quantity,
                   CONCAT(sp.price, ', ', d.sale)                                 AS price_and_sale,
                   SUM(sp.price * (1 - d.sale / 100.0))                           AS total_with_discount
            FROM sub_products sp
                     LEFT JOIN discounts d ON sp.id = d.sub_product_id
                     LEFT JOIN products p ON sp.product_id = p.id
                     LEFT JOIN brands b ON p.brand_id = b.id
                     WHERE p.created_at BETWEEN  ? AND  ? AND CAST(sp.article_number AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(p.name AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(b.name AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(p.created_at AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(sp.price AS TEXT) ILIKE (concat('%' || ? || '%'))
            GROUP BY p.id, sp.id, sp.article_number, p.name, p.created_at, sp.quantity, sp.price, d.sale, b.name
            ORDER BY sp.id
            """;
      } else if (productType.equalsIgnoreCase("В продаже")) {
        sql = """
            SELECT sp.id                                                          AS sub_product_id,
                   (SELECT spi.images
                    FROM sub_product_images spi
                    WHERE spi.sub_product_id = sp.id
                    LIMIT 1)                                                      AS image,
                   sp.article_number                                              AS article_number,
                   CONCAT(b.name, ' ', p.name)                                    AS name,
                   p.created_at                                                   AS date_of_creation,
                   sp.quantity                                                    AS quantity,
                   CONCAT(sp.price, ', ', d.sale)                                 AS price_and_sale,
                   SUM(sp.price * (1 - d.sale / 100.0))                           AS total_with_discount
            FROM sub_products sp
                     LEFT JOIN discounts d ON sp.id = d.sub_product_id
                     LEFT JOIN products p ON sp.product_id = p.id
                     LEFT JOIN brands b ON p.brand_id = b.id
                     WHERE sp.quantity>0 AND p.created_at BETWEEN  ? AND  ? AND CAST(sp.article_number AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(p.name AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(b.name AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(p.created_at AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(sp.price AS TEXT) ILIKE (concat('%' || ? || '%'))
            GROUP BY p.id, sp.id, sp.article_number, p.name, p.created_at, sp.quantity, sp.price, d.sale, b.name
            ORDER BY sp.id
            """;

      } else if (productType.equalsIgnoreCase("В избранном")) {
        sql = """
            SELECT sp.id                                                          AS sub_product_id,
                   (SELECT spi.images
                    FROM sub_product_images spi
                    WHERE spi.sub_product_id = sp.id
                    LIMIT 1)                                                      AS image,
                   sp.article_number                                              AS article_number,
                   CONCAT(b.name, ' ', p.name)                                    AS name,
                   p.created_at                                                   AS date_of_creation,
                   sp.quantity                                                    AS quantity,
                   CONCAT(sp.price, ', ', d.sale)                                 AS price_and_sale,
                   SUM(sp.price * (1 - d.sale / 100.0))                           AS total_with_discount
            FROM sub_products sp
                     LEFT JOIN discounts d ON sp.id = d.sub_product_id
                     LEFT JOIN products p ON sp.product_id = p.id
                     LEFT JOIN brands b ON p.brand_id = b.id
                     JOIN user_favorite f ON f.favorite = sp.id
            WHERE p.created_at BETWEEN  ? AND  ? AND CAST(sp.article_number AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(p.name AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(b.name AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(p.created_at AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(sp.price AS TEXT) ILIKE (concat('%' || ? || '%'))
            GROUP BY p.id, sp.id, sp.article_number, p.name, p.created_at, sp.quantity, sp.price, d.sale, b.name
            ORDER BY sp.id
            """;
      } else if (productType.equalsIgnoreCase("В корзине")) {
        sql = """
            SELECT sp.id                                                          AS sub_product_id,
                   (SELECT spi.images
                    FROM sub_product_images spi
                    WHERE spi.sub_product_id = sp.id
                    LIMIT 1)                                                      AS image,
                   sp.article_number                                              AS article_number,
                   CONCAT(b.name, ' ', p.name)                                    AS name,
                   p.created_at                                                   AS date_of_creation,
                   sp.quantity                                                    AS quantity,
                   CONCAT(sp.price, ', ', d.sale)                                 AS price_and_sale,
                   SUM(sp.price * (1 - d.sale / 100.0))                           AS total_with_discount
            FROM sub_products sp
                     LEFT JOIN discounts d ON sp.id = d.sub_product_id
                     LEFT JOIN products p ON sp.product_id = p.id
                     LEFT JOIN brands b ON p.brand_id = b.id
                     JOIN baskets_sub_products bsp ON sp.id = bsp.sub_products_id
                     JOIN baskets bas ON bsp.baskets_id = bas.id
                     JOIN users u ON bas.user_id = u.id
            WHERE p.created_at BETWEEN  ? AND  ? AND CAST(sp.article_number AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(p.name AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(b.name AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(p.created_at AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                        OR CAST(sp.price AS TEXT) ILIKE (concat('%' || ? || '%'))
            GROUP BY p.id, sp.id, sp.article_number, p.name, p.created_at, sp.quantity, sp.price, d.sale, b.name
            ORDER BY sp.id
            """;
      }
    } else {
      log.error("Product type is not correct");
      throw new BadRequestException("Product type is not correct");
    }

    int offset = (pageNumber - 1) * pageSize;
    sql += " LIMIT ? OFFSET ?";

    List<AdminSearchResponse> adminSearchResponses = jdbcTemplate.query(sql,
        (rs, rowNum) ->
            AdminSearchResponse
                .builder()
                .subProductId(rs.getLong("sub_product_id"))
                .image(rs.getString("image"))
                .articleNumber(rs.getInt("article_number"))
                .name(rs.getString("name"))
                .createdAt(rs.getDate("date_of_creation").toLocalDate())
                .quantity(rs.getInt("quantity"))
                .priceAndSale(rs.getString("price_and_sale"))
                .currentPrice(rs.getBigDecimal("total_with_discount"))
                .build(),
        startDate, endDate,
        keyword, keyword, keyword, keyword, keyword,
        pageSize, offset
    );

    log.info("Successfully!");
    return new AdminMainPagination(adminSearchResponses, pageSize, pageNumber, difference);
  }
}
