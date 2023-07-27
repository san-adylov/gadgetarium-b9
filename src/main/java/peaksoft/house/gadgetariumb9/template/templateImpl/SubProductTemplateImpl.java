package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductCatalogResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.dto.response.subProductResponse.SubProductCatalogAdminResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProductResponse.SubProductPaginationCatalogAdminResponse;
import peaksoft.house.gadgetariumb9.exceptions.BadRequestException;
import peaksoft.house.gadgetariumb9.template.SubProductTemplate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubProductTemplateImpl implements SubProductTemplate {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public SubProductPagination getProductFilter(
            SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber) {
        String sql = """
                   SELECT s.id,
                          d.sale,
                          (SELECT spi.images
                           FROM sub_product_images spi
                           WHERE spi.sub_product_id = s.id
                           LIMIT 1) AS image,
                          s.quantity,
                          p2.name,
                          s.price
                   FROM sub_products s
                            LEFT JOIN discounts d ON s.id = d.sub_product_id
                            JOIN phones p ON s.id = p.sub_product_id
                            JOIN laptops l ON s.id = l.sub_product_id
                            JOIN smart_watches sw ON s.id = sw.sub_product_id
                            LEFT JOIN products p2 ON s.product_id = p2.id
                            LEFT JOIN brands b ON p2.brand_id = b.id
                            JOIN sub_categories sc ON p2.sub_category_id = sc.id
                            JOIN categories c ON sc.category_id = c.id
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
        if (subProductCatalogRequest.getSorting().equalsIgnoreCase("Новинки")) {
            sql += " ORDER BY s.id DESC";
        } else if (subProductCatalogRequest.getSorting().equalsIgnoreCase("Все скидки")) {
            sql += " ORDER BY CASE WHEN d.sale > 0 THEN 0 END";
        } else if (subProductCatalogRequest.getSorting().equalsIgnoreCase("Свыше 50%")) {
            sql += "ORDER BY CASE WHEN d.sale > 50 THEN 1 END";
        } else if (subProductCatalogRequest.getSorting().equalsIgnoreCase("До 50%")) {
            sql += "ORDER BY CASE WHEN d.sale > 0 THEN 2 END";
        } else if (subProductCatalogRequest.getSorting().equalsIgnoreCase("По увеличению цены")) {
            sql += "ORDER BY s.price ASC";
        } else if (subProductCatalogRequest.getSorting().equalsIgnoreCase("По уменьшению цены")) {
            sql += "ORDER BY s.price DESC";
        } else if (subProductCatalogRequest.getSorting().equalsIgnoreCase("Рекомендуемые")) {
            sql += "ORDER BY s.rating DESC";
        }
        int offset = (pageNumber - 1) * pageSize;
        sql += " LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add(offset);
        List<SubProductCatalogResponse> subProductCatalogResponses = jdbcTemplate.query(sql,
                (rs, rowNum) -> new SubProductCatalogResponse(
                        rs.getLong("id"),
                        rs.getInt("sale"),
                        rs.getString("image"),
                        rs.getInt("quantity"),
                        rs.getString("name"),
                        rs.getBigDecimal("price")
                ), params.toArray());
        log.info("Filtering completed successfully");
        return new SubProductPagination(subProductCatalogResponses, pageSize, pageNumber);
    }

    @Override
    public SubProductPaginationCatalogAdminResponse getGetAllSubProductAdmin(String productTyp, int pageSize, int pageNumber) {
        List<SubProductCatalogAdminResponse> subProductCatalogAdminResponses = new ArrayList<>();
        String sql = "";
        if (productTyp != null) {
            if (productTyp.equalsIgnoreCase("Все товары")) {
                sql = """
                        SELECT s.id                                                                     AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                                                                AS images,
                               s.article_number                                                         AS articleNumber,
                               CONCAT(b.name, ' ', p2.name) AS name,
                               p2.data_of_issue                                                         AS dateOfCreation,
                               s.quantity                                                               AS quantity,
                               CONCAT(s.price, ', ', d.sale)                                            AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0))                                      AS total_with_discount                                                         
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                        GROUP BY s.id,s.article_number, p2.data_of_issue,s.quantity, s.price, d.sale, b.name, p2.name
                        """;
            } else if (productTyp.equalsIgnoreCase("В продаже")) {
                sql = """
                        SELECT s.id                                                                     AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                                                                AS images,
                               s.article_number                                                         AS articleNumber,
                               CONCAT(b.name, ' ', p2.name) AS name,
                               p2.data_of_issue                                                         AS dateOfCreation,
                               s.quantity                                                               AS quantity,
                               CONCAT(s.price, ', ', d.sale)                                            AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0))                                      AS total_with_discount                                                         
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                                 where s.quantity>0
                        GROUP BY s.id,s.article_number, p2.data_of_issue,s.quantity, s.price, d.sale, b.name, p2.name
                        """;
            } else if (productTyp.equalsIgnoreCase("В избранном")) {

            } else if (productTyp.equalsIgnoreCase("В карзине")) {
                sql += """
                        SELECT
                            s.id AS subProductId,
                            COALESCE(
                                (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1),' '
                            ) AS images,
                            u.favorite,
                            u.user_id,
                            s.article_number AS articleNumber,
                            CONCAT(b.name, ' ', p2.name) AS name,
                            p2.data_of_issue AS dateOfCreation,
                            s.quantity AS quantity,
                            CONCAT(s.price, ', ', d.sale) AS price_and_sale,
                            SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount
                        FROM
                            sub_products s
                        JOIN
                            user_favorite u ON u.favorite = s.id
                        LEFT JOIN
                            discounts d ON s.id = d.sub_product_id
                        LEFT JOIN
                            products p2 ON s.product_id = p2.id
                        LEFT JOIN
                            brands b ON p2.brand_id = b.id
                        GROUP BY
                            s.id,
                            u.favorite,
                            u.user_id,
                            s.article_number,
                            CONCAT(b.name, ' ', p2.name),
                            p2.data_of_issue,
                            s.quantity,
                            CONCAT(s.price, ', ', d.sale);
                                             
                           """;
            } else if (productTyp.equalsIgnoreCase("Новинки")) {
                sql = """
                         SELECT s.id                                                                     AS subProductId,
                                                       (SELECT spi.images
                                                        FROM sub_product_images spi
                                                        WHERE spi.sub_product_id = s.id
                                                        LIMIT 1)                                                                AS images,
                                                       s.article_number                                                         AS articleNumber,
                                                       CONCAT(b.name, ' ', p2.name) AS name,
                                                       p2.data_of_issue                                                         AS dateOfCreation,
                                                       s.quantity                                                               AS quantity,
                                                       CONCAT(s.price, ', ', d.sale)                                            AS price_and_sale,
                                                       SUM(s.price * (1 - d.sale / 100.0))                                      AS total_with_discount                                                         
                                                FROM sub_products s
                                                         LEFT JOIN discounts d ON s.id = d.sub_product_id
                                                         LEFT JOIN products p2 ON s.product_id = p2.id
                                                         LEFT JOIN brands b ON p2.brand_id = b.id
                                                GROUP BY s.id,s.article_number, p2.data_of_issue,s.quantity, s.price, d.sale, b.name, p2.name ORDER BY s.id desc 
                        """;
            } else if (productTyp.equalsIgnoreCase("По акции")) {
                if (productTyp.equalsIgnoreCase("Все акции")) {
                    sql = """
                            SELECT s.id                                                                     AS subProductId,
                                                          (SELECT spi.images
                                                           FROM sub_product_images spi
                                                           WHERE spi.sub_product_id = s.id
                                                           LIMIT 1)                                                                AS images,
                                                          s.article_number                                                         AS articleNumber,
                                                          CONCAT(b.name, ' ', p2.name) AS name,
                                                          p2.data_of_issue                                                         AS dateOfCreation,
                                                          s.quantity                                                               AS quantity,
                                                          CONCAT(s.price, ', ', d.sale)                                            AS price_and_sale,
                                                          SUM(s.price * (1 - d.sale / 100.0))                                      AS total_with_discount                                                         
                                                   FROM sub_products s
                                                            LEFT JOIN discounts d ON s.id = d.sub_product_id
                                                            LEFT JOIN products p2 ON s.product_id = p2.id
                                                            LEFT JOIN brands b ON p2.brand_id = b.id
                                                            
                                                   GROUP BY s.id,s.article_number, p2.data_of_issue,s.quantity, s.price, d.sale, b.name, p2.name""";

                } else if (productTyp.equalsIgnoreCase("До 50%")) {
                    sql = """
                            SELECT s.id                                AS subProductId,
                                   (SELECT spi.images
                                    FROM sub_product_images spi
                                    WHERE spi.sub_product_id = s.id
                                    LIMIT 1)                           AS images,
                                   s.article_number                    AS articleNumber,
                                   CONCAT(b.name, ' ', p2.name)        AS name,
                                   p2.data_of_issue                    AS dateOfCreation,
                                   s.quantity                          AS quantity,
                                   CONCAT(s.price, ', ', d.sale)       AS price_and_sale,
                                   SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount
                            FROM sub_products s
                                     LEFT JOIN discounts d ON s.id = d.sub_product_id
                                     LEFT JOIN products p2 ON s.product_id = p2.id
                                     LEFT JOIN brands b ON p2.brand_id = b.id
                            where d.sale<50
                            GROUP BY s.id, s.article_number, p2.data_of_issue, s.quantity, s.price, d.sale, b.name, p2.name
                            """;

                } else if (productTyp.equalsIgnoreCase("Свыше 50%")) {
                    sql = """
                            SELECT s.id                                AS subProductId,
                                   (SELECT spi.images
                                    FROM sub_product_images spi
                                    WHERE spi.sub_product_id = s.id
                                    LIMIT 1)                           AS images,
                                   s.article_number                    AS articleNumber,
                                   CONCAT(b.name, ' ', p2.name)        AS name,
                                   p2.data_of_issue                    AS dateOfCreation,
                                   s.quantity                          AS quantity,
                                   CONCAT(s.price, ', ', d.sale)       AS price_and_sale,
                                   SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount
                            FROM sub_products s
                                     LEFT JOIN discounts d ON s.id = d.sub_product_id
                                     LEFT JOIN products p2 ON s.product_id = p2.id
                                     LEFT JOIN brands b ON p2.brand_id = b.id
                            where d.sale>=50
                            GROUP BY s.id, s.article_number, p2.data_of_issue, s.quantity, s.price, d.sale, b.name, p2.name
                            """;
                }
            } else if (productTyp.equalsIgnoreCase("Рекомендуемые")) {
                sql = """
                        SELECT s.id                                AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                           AS images,
                               s.article_number                    AS articleNumber,
                               CONCAT(b.name, ' ', p2.name)        AS name,
                               p2.data_of_issue                    AS dateOfCreation,
                               s.quantity                          AS quantity,
                               CONCAT(s.price, ', ', d.sale)       AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                        GROUP BY s.id, s.article_number, p2.data_of_issue, s.quantity, s.price, d.sale, b.name, p2.name, s.rating order by s.rating desc;
                        """;
            } else if (productTyp.equalsIgnoreCase("По увеличению цены")) {
                sql = """
                        SELECT s.id                                AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                           AS images,
                               s.article_number                    AS articleNumber,
                               CONCAT(b.name, ' ', p2.name)        AS name,
                               p2.data_of_issue                    AS dateOfCreation,
                               s.quantity                          AS quantity,
                               CONCAT(s.price, ', ', d.sale)       AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                        GROUP BY s.id, s.article_number, p2.data_of_issue, s.quantity, s.price, d.sale, b.name, p2.name, s.rating order by s.price desc;
                        """;
            } else if (productTyp.equalsIgnoreCase("По уменьшению цены")) {
                sql = """
                        SELECT s.id                                AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                           AS images,
                               s.article_number                    AS articleNumber,
                               CONCAT(b.name, ' ', p2.name)        AS name,
                               p2.data_of_issue                    AS dateOfCreation,
                               s.quantity                          AS quantity,
                               CONCAT(s.price, ', ', d.sale)       AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                        GROUP BY s.id, s.article_number, p2.data_of_issue, s.quantity, s.price, d.sale, b.name, p2.name, s.rating order by s.price asc;
                        """;
            }
        } else {
            log.error("Product type is not correct");
            throw new BadRequestException("Product type is not correct");
        }


        List<SubProductCatalogAdminResponse> catalogAdminResponseList = jdbcTemplate.query(sql,
                (rs, rowNum) ->
                        SubProductCatalogAdminResponse
                                .builder()
                                .subProductId(rs.getLong("subProductId"))
                                .images(rs.getString("images"))
                                .productNameAndBrandName(rs.getString("name"))
                                .articleNumber(rs.getLong("articleNumber"))
                                .dateOfCreation(rs.getDate("dateOfCreation").toLocalDate())
                                .quantity(rs.getInt("quantity"))
                                .price_and_sale(rs.getString("price_and_sale"))
                                .total_with_discount(rs.getBigDecimal("total_with_discount"))
                                .build()
        );

        log.info("Successfully");
        return new

                SubProductPaginationCatalogAdminResponse(pageSize, pageNumber, catalogAdminResponseList);
    }
}