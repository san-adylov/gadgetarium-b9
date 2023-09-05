package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.compare.*;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.*;
import peaksoft.house.gadgetariumb9.enums.Processor;
import peaksoft.house.gadgetariumb9.enums.Purpose;
import peaksoft.house.gadgetariumb9.exceptions.BadRequestException;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import peaksoft.house.gadgetariumb9.template.SubProductTemplate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubProductTemplateImpl implements SubProductTemplate {
    private final UserRepository userRepository;

    private final JdbcTemplate jdbcTemplate;

    private final JwtService jwtService;


    private List<Integer> pageSizeAndOffset(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return Arrays.asList(pageSize, offset);
    }

    private String email() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<Long> getFavorites() {
        List<Long> favorites = Collections.emptyList();
        if (!email().equalsIgnoreCase("anonymousUser")) {
            User user = userRepository.getUserByEmail(email())
                    .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email())));
            favorites = jdbcTemplate.queryForList(
                    "SELECT uf.favorite FROM user_favorite uf WHERE uf.user_id = ?",
                    Long.class,
                    user.getId());
        }
        return favorites;
    }

    public List<Long> getComparison() {
        List<Long> comparisons = Collections.emptyList();
        if (!email().equalsIgnoreCase("anonymousUser")) {
            User user = userRepository.getUserByEmail(email())
                    .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email())));
            comparisons = jdbcTemplate.queryForList(
                    "SELECT uc.comparison FROM user_comparison uc WHERE uc.user_id = ?",
                    Long.class,
                    user.getId());
        }
        return comparisons;
    }

    @Override
    public SubProductPagination getProductFilter(SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber) {
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
                            LEFT JOIN phones p ON s.id = p.sub_product_id
                            LEFT JOIN laptops l ON s.id = l.sub_product_id
                            LEFT JOIN smart_watches sw ON s.id = sw.sub_product_id
                            LEFT JOIN products p2 ON s.product_id = p2.id
                            LEFT JOIN brands b ON p2.brand_id = b.id
                            JOIN sub_categories sc ON p2.sub_category_id = sc.id
                            JOIN categories c ON p2.category_id = c.id
                   WHERE c.title ILIKE ?
                """;
        List<Object> params = new ArrayList<>();
        params.add(subProductCatalogRequest.getGadgetType());
        if (subProductCatalogRequest.getBrandIds().get(0) > 0) {
            sql += "AND b.id = ANY (?)";
            params.add(subProductCatalogRequest.getBrandIds().toArray(new Long[0]));
        }
        if (subProductCatalogRequest.getPriceStart().compareTo(BigDecimal.ZERO) != 0) {
            sql += " AND (s.price >= ?)";
            params.add(subProductCatalogRequest.getPriceStart());
        }
        if (subProductCatalogRequest.getPriceEnd().compareTo(BigDecimal.ZERO) != 0) {
            sql += " AND (s.price <= ?)";
            params.add(subProductCatalogRequest.getPriceEnd());
        }
        if (!subProductCatalogRequest.getCodeColor().get(0).equalsIgnoreCase("string")) {
            sql += " AND (s.code_color = ANY (?))";
            params.add(subProductCatalogRequest.getCodeColor().toArray(new String[0]));
        }
        if (subProductCatalogRequest.getRam().get(0) > 0) {
            sql += " AND (s.ram = ANY (?))";
            params.add(subProductCatalogRequest.getRam().toArray(new Integer[0]));
        }
        if (subProductCatalogRequest.getRom().get(0) > 0) {
            sql += " AND (s.rom = ANY (?))";
            params.add(subProductCatalogRequest.getRom().toArray(new Integer[0]));
        }
        if (subProductCatalogRequest.getSim().get(0) > 0) {
            sql += " AND (p.sim = ?)";
            params.add(subProductCatalogRequest.getSim());
        }
        if (!subProductCatalogRequest.getBatteryCapacity().get(0).equalsIgnoreCase("string")) {
            sql += " AND (p.battery_capacity = ANY (?))";
            params.add(subProductCatalogRequest.getBatteryCapacity().toArray(new String[0]));
        }
        if (subProductCatalogRequest.getScreenSize().get(0) > 0) {
            sql += " AND (p.screen_size = ANY (?))";
            params.add(subProductCatalogRequest.getScreenSize().toArray(new Double[0]));
        }
        if (!subProductCatalogRequest.getScreenResolution().get(0).equalsIgnoreCase("string")) {
            sql += " AND (s.screen_resolution = ANY (?))";
            params.add(subProductCatalogRequest.getScreenResolution().toArray(new String[0]));
        }
        if (!subProductCatalogRequest.getProcessors().get(0).equalsIgnoreCase("string")) {
            sql += " AND (l.processor = ANY (?))";
            params.add(subProductCatalogRequest.getProcessors().toArray(new String[0]));
        }
        if (!subProductCatalogRequest.getPurposes().get(0).equalsIgnoreCase("string")) {
            sql += " AND (l.purpose = ANY (?))";
            params.add(subProductCatalogRequest.getPurposes().toArray(new String[0]));
        }
        if (subProductCatalogRequest.getVideoMemory() != null && subProductCatalogRequest.getVideoMemory().get(0) > 0) {
            sql += " AND (l.video_memory = ANY(?))";
            params.add(subProductCatalogRequest.getVideoMemory().toArray(new Integer[0]));
        }
        if (!subProductCatalogRequest.getHousingMaterials().get(0).equalsIgnoreCase("string")) {
            sql += " AND (sw.housing_material = ANY (?))";
            params.add(subProductCatalogRequest.getHousingMaterials().toArray(new String[0]));
        }
        if (!subProductCatalogRequest.getMaterialBracelets().get(0).equalsIgnoreCase("string")) {
            sql += " AND (sw.material_bracelet = ANY(?))";
            params.add(subProductCatalogRequest.getMaterialBracelets().toArray(new String[0]));
        }
        if (!subProductCatalogRequest.getGenders().get(0).equalsIgnoreCase("string")) {
            sql += " AND (sw.gender = ANY(?))";
            params.add(subProductCatalogRequest.getGenders().toArray(new String[0]));
        }
        if (!subProductCatalogRequest.getInterfaces().get(0).equalsIgnoreCase("string")) {
            sql += " AND (sw.an_interface = ANY(?))";
            params.add(subProductCatalogRequest.getInterfaces().toArray(new String[0]));
        }
        if (!subProductCatalogRequest.getHullShapes().get(0).equalsIgnoreCase("string")) {
            sql += " AND (sw.hull_shape = ANY(?))";
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
        sql += " LIMIT ? OFFSET ?";
        params.add(pageSizeAndOffset(pageNumber, pageSize).get(0));
        params.add(pageSizeAndOffset(pageNumber, pageSize).get(1));
        List<SubProductCatalogResponse> subProductCatalogResponses = jdbcTemplate.query(sql, (rs, rowNum) -> new SubProductCatalogResponse(rs.getLong("id"), rs.getInt("sale"), rs.getString("image"), rs.getInt("quantity"), rs.getString("name"), rs.getBigDecimal("price")), params.toArray());
        log.info("Filtering completed successfully");
        List<Long> favorites = getFavorites();

        for (SubProductCatalogResponse s : subProductCatalogResponses) {
            s.setFavorite(favorites.contains(s.getId()));
        }

        List<Long> comparisons = getComparison();

        for (SubProductCatalogResponse s : subProductCatalogResponses) {
            s.setComparison(comparisons.contains(s.getId()));
        }
        return new SubProductPagination(subProductCatalogResponses, pageSize, pageNumber);
    }

    @Override
    public InfographicsResponse infographics(String period) {
        log.info("Get infographic!");
        String sql = """
                SELECT COALESCE(SUM(CASE WHEN o.status = 'DELIVERED' THEN total_price END), 0) AS delivered_total_price,
                       COALESCE(COUNT(CASE WHEN o.status = 'DELIVERED' THEN total_price END), 0) AS delivered_quantity,
                       COALESCE(SUM(CASE WHEN o.status = 'EXPECTATION' OR o.status = 'PROCESSING' OR o.status = 'COURIER_ON_THE_WAY' OR o.status = 'READY_FOR_DELIVERY' THEN total_price END), 0) AS waiting_total_price,
                       COALESCE(COUNT(CASE WHEN o.status = 'EXPECTATION' OR o.status = 'PROCESSING' OR o.status = 'COURIER_ON_THE_WAY' OR o.status = 'READY_FOR_DELIVERY' THEN total_price END), 0) AS waiting_quantity,
                       COALESCE(SUM(CASE
                               WHEN o.status = 'DELIVERED' AND o.date_of_order = current_date and 'day' = ? THEN total_price
                               WHEN o.status = 'DELIVERED' AND extract(year from o.date_of_order) = extract(year from current_date) and
                                 extract(month from o.date_of_order) = extract(month from current_date) and 'month' = ? THEN total_price
                               WHEN o.status = 'DELIVERED' AND extract(year from o.date_of_order) = extract(year from current_date) and 'year' = ?
                                   THEN total_price END),0)
                       AS current_period,
                       COALESCE(SUM(CASE
                               WHEN o.status = 'DELIVERED' AND o.date_of_order = (current_date - interval '1 day') and ? = 'day' THEN total_price
                               WHEN o.status = 'DELIVERED' AND extract(year from o.date_of_order) = extract(year from current_date) and
                                 extract(month from o.date_of_order) = extract(month from current_date - interval '1 month') and
                                    ? = 'month' THEN total_price
                               WHEN o.status = 'DELIVERED' AND extract(year from o.date_of_order) = extract(year from current_date - interval '1 year') and ? = 'year'
                                   THEN total_price END),0)
                       AS previous_period
                FROM orders o;
                """;

        return jdbcTemplate.query(sql, (resulSet, i) -> new InfographicsResponse(resulSet.getBigDecimal("delivered_total_price"), resulSet.getInt("delivered_quantity"), resulSet.getBigDecimal("waiting_total_price"), resulSet.getInt("waiting_quantity"), resulSet.getBigDecimal("current_period"), resulSet.getBigDecimal("previous_period")), period, period, period, period, period, period).stream().findFirst().orElseThrow(() -> {
            log.error("This answer is not found!");
            return new NotFoundException("This answer is not found!");
        });
    }

    @Override
    public List<SubProductHistoryResponse> getRecentlyViewedProducts() {
        User user = jwtService.getAuthenticationUser();
        String sql = """
                SELECT s.id,
                       (SELECT spi.images
                        FROM sub_product_images spi
                        WHERE spi.sub_product_id = s.id
                        LIMIT 1)                    AS image,
                       CONCAT(c.title, ' ', p.name) AS name,
                       s.rating,
                       s.price
                FROM sub_products s
                         JOIN products p ON s.product_id = p.id
                         JOIN categories c ON p.category_id = c.id
                         JOIN user_recently_viewed_products urvp ON urvp.recently_viewed_products = s.id
                         JOIN users u on urvp.user_id = u.id
                WHERE u.id = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new SubProductHistoryResponse(
                rs.getLong("id"),
                rs.getString("image"),
                rs.getString("name"),
                rs.getDouble("rating"),
                rs.getBigDecimal("price")
        ), user.getId());
    }

    private int getQuantityCount(String query) {
        Integer quantityCount = jdbcTemplate.queryForObject(query, Integer.class);
        return quantityCount != null ? quantityCount : 0;
    }

    @Override
    public SubProductPaginationCatalogAdminResponse getGetAllSubProductAdmin(String productType, LocalDate startDate, LocalDate endDate, int pageSize, int pageNumber) {
        String subProductQuery = "SELECT sum(s.quantity) from sub_products s";
        String orderQuery = "SELECT sum(o.quantity) from orders o";
        int subProductQuantityCount = getQuantityCount(subProductQuery);
        int orderQuantityCount = getQuantityCount(orderQuery);
        int difference = orderQuantityCount - subProductQuantityCount;
        String sql = "";
        if (productType != null) {
            if (productType.equalsIgnoreCase("Все товары")) {
                sql = """
                        SELECT s.id                                                                     AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                                                                AS images,
                               s.article_number                                                         AS articleNumber,
                               CONCAT(b.name, ' ', p2.name) AS name,
                               p2.created_at                                                         AS dateOfCreation,
                               s.quantity                                                               AS quantity,
                               CONCAT(s.price, ', ', d.sale)                                            AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0))                                      AS total_with_discount  ,
                               s.rating
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                                 WHERE  p2.created_at between  ? and  ?
                        GROUP BY s.id,s.article_number, p2.created_at,s.quantity, s.price, d.sale, b.name, p2.name,s.rating
                        """;
            } else if (productType.equalsIgnoreCase("В продаже")) {
                sql = """
                        SELECT s.id                                                                     AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                                                                AS images,
                               s.article_number                                                         AS articleNumber,
                               CONCAT(b.name, ' ', p2.name) AS name,
                               p2.created_at                                                         AS dateOfCreation,
                               s.quantity                                                               AS quantity,
                               CONCAT(s.price, ', ', d.sale)                                            AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0))                                      AS total_with_discount     ,
                               s.rating
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                                 where s.quantity>0 and  p2.created_at between  ? and  ?
                        GROUP BY s.id,s.article_number, p2.created_at,s.quantity, s.price, d.sale, b.name, p2.name, s.rating
                        """;
            } else if (productType.equalsIgnoreCase("Новинки")) {
                sql = """
                         SELECT s.id                                                                     AS subProductId,
                                                       (SELECT spi.images
                                                        FROM sub_product_images spi
                                                        WHERE spi.sub_product_id = s.id
                                                        LIMIT 1)                                                                AS images,
                                                       s.article_number                                                         AS articleNumber,
                                                       CONCAT(b.name, ' ', p2.name) AS name,
                                                       p2.created_at                                                         AS dateOfCreation,
                                                       s.quantity                                                               AS quantity,
                                                       CONCAT(s.price, ', ', d.sale)                                            AS price_and_sale,
                                                       SUM(s.price * (1 - d.sale / 100.0))                                      AS total_with_discount  ,
                                                       s.rating
                                                FROM sub_products s
                                                         LEFT JOIN discounts d ON s.id = d.sub_product_id
                                                         LEFT JOIN products p2 ON s.product_id = p2.id
                                                         LEFT JOIN brands b ON p2.brand_id = b.id
                                                         WHERE  p2.created_at between  ? and  ?
                                                GROUP BY s.id,s.article_number, p2.created_at,s.quantity, s.price, d.sale, b.name, p2.name, s.rating ORDER BY s.id desc
                        """;
            } else if (productType.equalsIgnoreCase("Все акции")) {
                sql = """
                        SELECT s.id                                                                     AS subProductId,
                                                      (SELECT spi.images
                                                       FROM sub_product_images spi
                                                       WHERE spi.sub_product_id = s.id
                                                       LIMIT 1)                                                                AS images,
                                                      s.article_number                                                         AS articleNumber,
                                                      CONCAT(b.name, ' ', p2.name) AS name,
                                                      p2.created_at                                                         AS dateOfCreation,
                                                      s.quantity                                                               AS quantity,
                                                      CONCAT(s.price, ', ', d.sale)                                            AS price_and_sale,
                                                      SUM(s.price * (1 - d.sale / 100.0))                                      AS total_with_discount,
                                                      s.rating
                                               FROM sub_products s
                                                        LEFT JOIN discounts d ON s.id = d.sub_product_id
                                                        LEFT JOIN products p2 ON s.product_id = p2.id
                                                        LEFT JOIN brands b ON p2.brand_id = b.id
                                                        WHERE  p2.created_at between  ? and  ?
                                               GROUP BY s.id,s.article_number, p2.created_at,s.quantity, s.price, d.sale, b.name, p2.name, s.rating""";

            } else if (productType.equalsIgnoreCase("В избранном")) {
                sql = """
                        SELECT s.id AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1) AS images,
                               s.article_number AS articleNumber,
                               CONCAT(b.name, ' ', p2.name) AS productFullName,
                               p2.created_at AS dateOfCreation,
                               s.quantity AS quantity,
                               CONCAT(s.price, ', ', d.sale) AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount,
                               s.rating
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                                 JOIN user_favorite f ON f.user_id = p2.id
                        where p2.created_at between  ? and  ?
                        GROUP BY s.id, s.article_number, p2.created_at, s.quantity, s.price, d.sale, b.name, p2.name, s.rating
                        LIMIT ? ;
                        """;
            } else if (productType.equalsIgnoreCase("В корзине")) {
                sql = """
                        SELECT s.id AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1) AS images,
                               s.article_number AS articleNumber,
                               CONCAT(b.name, ' ', p2.name) AS productFullName,
                               p2.created_at AS dateOfCreation,
                               s.quantity AS quantity,
                               CONCAT(s.price, ', ', d.sale) AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount,
                               s.rating
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                                 JOIN baskets_sub_products bsp ON s.id = bsp.sub_products_id
                                 JOIN baskets bas ON bsp.baskets_id = bas.id
                                 JOIN users u ON bas.user_id = u.id
                        where p2.created_at between  ? and  ?
                        GROUP BY s.id, s.article_number, p2.created_at, s.quantity, s.price, d.sale, b.name, p2.name, s.rating
                        ORDER BY s.id
                        LIMIT ? ;
                          """;
            } else if (productType.equalsIgnoreCase("До 50%")) {
                sql = """
                        SELECT s.id                                AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                           AS images,
                               s.article_number                    AS articleNumber,
                               CONCAT(b.name, ' ', p2.name)        AS name,
                               p2.created_at                    AS dateOfCreation,
                               s.quantity                          AS quantity,
                               CONCAT(s.price, ', ', d.sale)       AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount,
                               s.rating
                                                 
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                        where d.sale<50 and  p2.created_at between  ? and  ?
                        GROUP BY s.id, s.article_number, p2.created_at, s.quantity, s.price, d.sale, b.name, p2.name,s.rating
                        """;

            } else if (productType.equalsIgnoreCase("Свыше 50%")) {
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
                               SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount,
                               s.rating
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                        where d.sale>=50 and   p2.created_at between  ? and  ?
                        GROUP BY s.id, s.article_number, p2.data_of_issue, s.quantity, s.price, d.sale, b.name, p2.name,s.rating
                        """;
            } else if (productType.equalsIgnoreCase("Рекомендуемые")) {
                sql = """
                        SELECT s.id                                AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                           AS images,
                               s.article_number                    AS articleNumber,
                               CONCAT(b.name, ' ', p2.name)        AS name,
                               p2.created_at                    AS dateOfCreation,
                               s.quantity                          AS quantity,
                               CONCAT(s.price, ', ', d.sale)       AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount,
                               s.rating
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                                 WHERE  p2.created_at between  ? and  ?
                        GROUP BY s.id, s.article_number, p2.created_at, s.quantity, s.price, d.sale, b.name, p2.name, s.rating order by s.rating desc;
                        """;
            } else if (productType.equalsIgnoreCase("По увеличению цены")) {
                sql = """
                        SELECT s.id                                AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                           AS images,
                               s.article_number                    AS articleNumber,
                               CONCAT(b.name, ' ', p2.name)        AS name,
                               p2.created_at                    AS dateOfCreation,
                               s.quantity                          AS quantity,
                               CONCAT(s.price, ', ', d.sale)       AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount,
                               s.rating
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                                 WHERE  p2.created_at between  ? and  ?
                        GROUP BY s.id, s.article_number, p2.created_at, s.quantity, s.price, d.sale, b.name, p2.name, s.rating order by s.price desc;
                        """;
            } else if (productType.equalsIgnoreCase("По уменьшению цены")) {
                sql = """
                        SELECT s.id                                AS subProductId,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = s.id
                                LIMIT 1)                           AS images,
                               s.article_number                    AS articleNumber,
                               CONCAT(b.name, ' ', p2.name)        AS name,
                               p2.created_at                    AS dateOfCreation,
                               s.quantity                          AS quantity,
                               CONCAT(s.price, ', ', d.sale)       AS price_and_sale,
                               SUM(s.price * (1 - d.sale / 100.0)) AS total_with_discount,
                               s.rating
                        FROM sub_products s
                                 LEFT JOIN discounts d ON s.id = d.sub_product_id
                                 LEFT JOIN products p2 ON s.product_id = p2.id
                                 LEFT JOIN brands b ON p2.brand_id = b.id
                                 WHERE  p2.created_at between  ? and  ?
                        GROUP BY s.id, s.article_number, p2.created_at, s.quantity, s.price, d.sale, b.name, p2.name, s.rating order by s.price;
                        """;
            }
        } else {
            log.error("Product type is not correct");
            throw new BadRequestException("Product type is not correct");
        }

        int offset = (pageNumber - 1) * pageSize;
        sql += " LIMIT ? OFFSET ?";

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
                                .rating(rs.getDouble("rating"))
                                .build(),
                startDate, endDate, pageSize, offset
        );

        log.info("Successfully");
        return new SubProductPaginationCatalogAdminResponse(pageSize, pageNumber, difference, catalogAdminResponseList);
    }

    @Override
    public List<LatestComparison> getLatestComparison() {
        User user = jwtService.getAuthenticationUser();
        return jdbcTemplate.query("""
                        SELECT sp.id,
                               (SELECT spi.images
                                FROM sub_product_images spi
                                WHERE spi.sub_product_id = sp.id
                                LIMIT 1)                   AS image,
                               concat(b.name, ' ', p.name) AS name,
                               sp.price
                        FROM sub_products sp
                                 JOIN products p ON sp.product_id = p.id
                                 JOIN brands b ON p.brand_id = b.id
                                 JOIN user_comparison uc ON uc.comparison = sp.id
                                 JOIN users u ON uc.user_id = u.id
                        WHERE u.id = ?         
                                                """, (rs, rowNum) ->
                        LatestComparison
                                .builder()
                                .subProductId(rs.getLong("id"))
                                .image(rs.getString("image"))
                                .name(rs.getString("name"))
                                .price(rs.getBigDecimal("price"))
                                .build(),
                user.getId());
    }

    public List<CompareProductResponse> getCompareParameters(String productName) {
        User user = jwtService.getAuthenticationUser();
        String sql = """                        
                SELECT DISTINCT sp.id                  AS id,
                                p.id                   AS product_id,
                                b.name                 AS brand_name,
                                p.name                 AS product_name,
                                sp.price               AS price,
                                sp.code_color          AS color,
                                sp.screen_resolution   AS screen,
                                sp.rom                 AS rom,
                                sp.additional_features AS operational_systems,
                                cat.title              AS category_title,
                                sc.title               AS sub_category_title,
                                ph.sim                 AS sim,
                                ph.diagonal_screen,
                                ph.battery_capacity    AS battery_capacity,
                                l.processor,
                                l.purpose,
                                l.screen_size          AS screen_size,
                                l.video_memory,
                                sm.an_interface        AS interface,
                                sm.hull_shape          AS hull_shape,
                                sm.material_bracelet   AS bracelet_material,
                                sm.housing_material    AS housing_material,
                                sm.gender              AS gender,
                                sm.waterproof          AS waterproof,
                                sm.display_discount    AS display_discount,
                                (SELECT spi.images
                                 FROM sub_product_images spi
                                 WHERE spi.sub_product_id = sp.id
                                 LIMIT 1)              AS image
                FROM sub_products sp
                         JOIN products p ON p.id = sp.product_id
                         JOIN brands b ON b.id = p.brand_id
                         JOIN categories cat ON cat.id = p.category_id
                         JOIN sub_categories sc ON sc.id = p.sub_category_id
                         LEFT JOIN phones ph ON ph.sub_product_id = sp.id
                         LEFT JOIN laptops l ON l.sub_product_id = sp.id
                         LEFT JOIN smart_watches sm ON sm.sub_product_id = sp.id
                         JOIN user_comparison uc ON uc.comparison = sp.id
                         JOIN users u ON uc.user_id = u.id
                WHERE cat.title ILIKE ? AND u.id = ?
                   """;
        if (productName.equalsIgnoreCase("Phone") || productName.equalsIgnoreCase("Tablet")) {
            return jdbcTemplate.query(
                    sql,
                    ((rs, rowNum) -> CompareSmartPhoneResponse
                            .builder()
                            .subProductId(rs.getLong("id"))
                            .prId(rs.getLong("product_id"))
                            .brandName(rs.getString("brand_name"))
                            .prodName(rs.getString("product_name"))
                            .price(rs.getBigDecimal("price"))
                            .color(rs.getString("color"))
                            .screen(rs.getString("screen"))
                            .rom(rs.getInt("rom"))
                            .operationalSystems(rs.getString("operational_systems"))
                            .catTitle(rs.getString("category_title"))
                            .subCatTitle(rs.getString("sub_category_title"))
                            .image(rs.getString("image"))
                            .simCard(rs.getInt("sim"))
                            .diagonalScreen(rs.getString("diagonal_screen"))
                            .batteryCapacity(rs.getString("battery_capacity"))
                            .screenSize(rs.getDouble("screen_size"))
                            .build()),
                    "%Phone%", user.getId());
        } else if (productName.equalsIgnoreCase("Laptop")) {
            return jdbcTemplate.query(
                    sql,
                    ((rs, rowNum) -> CompareLaptopResponse
                            .builder()
                            .subProductId(rs.getLong("id"))
                            .prId(rs.getLong("product_id"))
                            .brandName(rs.getString("brand_name"))
                            .prodName(rs.getString("product_name"))
                            .price(rs.getBigDecimal("price"))
                            .color(rs.getString("color"))
                            .screen(rs.getString("screen"))
                            .rom(rs.getInt("rom"))
                            .operationalSystems(rs.getString("operational_systems"))
                            .catTitle(rs.getString("category_title"))
                            .subCatTitle(rs.getString("sub_category_title"))
                            .image(rs.getString("image"))
                            .processor(Processor.valueOf(rs.getString("processor")))
                            .purpose(Purpose.valueOf(rs.getString("purpose")))
                            .screen_size(rs.getDouble("screen_size"))
                            .video_memory(rs.getInt("video_memory"))
                            .build()),
                    "%Laptop%", user.getId());
        } else if (productName.equalsIgnoreCase("Smart Watch")) {
            return jdbcTemplate.query(
                    sql,
                    ((rs, rowNum) -> CompareSmartWatchResponse
                            .builder()
                            .subProductId(rs.getLong("id"))
                            .prId(rs.getLong("product_id"))
                            .brandName(rs.getString("brand_name"))
                            .prodName(rs.getString("product_name"))
                            .price(rs.getBigDecimal("price"))
                            .color(rs.getString("color"))
                            .screen(rs.getString("screen"))
                            .rom(rs.getInt("rom"))
                            .operationalSystems(rs.getString("operational_systems"))
                            .catTitle(rs.getString("category_title"))
                            .subCatTitle(rs.getString("sub_category_title"))
                            .image(rs.getString("image"))
                            .anInterface(rs.getString("interface"))
                            .hullShape(rs.getString("hull_shape"))
                            .materialBracelet(rs.getString("bracelet_material"))
                            .housingMaterial(rs.getString("housing_material"))
                            .gender(rs.getString("gender"))
                            .waterproof(rs.getBoolean("waterproof"))
                            .displayDiscount(rs.getDouble("display_discount"))
                            .build())
                    , "%Smart Watch%", user.getId());
        }
        return null;
    }

    @Override
    public List<ComparisonCountResponse> countCompareUser() {
        User user = jwtService.getAuthenticationUser();
        String compare = """         
                SELECT c.title              AS categoryTitle,
                       COUNT(uc.comparison) AS comparisonCount
                FROM user_comparison uc
                         JOIN sub_products sp ON uc.comparison = sp.id
                         JOIN users u ON uc.user_id = u.id
                         JOIN products p ON p.id = sp.product_id
                         JOIN categories c ON c.id = p.category_id
                WHERE uc.user_id = ?
                  AND c.title IN ('Phone', 'Smart Watch', 'Tablet', 'Laptop')
                GROUP BY c.title;
                            """;
        return jdbcTemplate.query(
                compare,
                (rs) -> {
                    List<ComparisonCountResponse> responses = new ArrayList<>();
                    while (rs.next()) {
                        String categoryTitle = rs.getString("categoryTitle");
                        int comparisonCount = rs.getInt("comparisonCount");

                        ComparisonCountResponse response = new ComparisonCountResponse();
                        response.setCategoryTitle(categoryTitle);
                        response.setTotalCounter(comparisonCount);

                        responses.add(response);
                    }
                    return responses;
                },
                user.getId());
    }
}
