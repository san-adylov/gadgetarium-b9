package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.MainPagePaginationResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductMainPageResponse;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import peaksoft.house.gadgetariumb9.template.MainPageProducts;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MainPageProductsImpl implements MainPageProducts {

    private final JdbcTemplate jdbcTemplate;

    private final UserRepository userRepository;

    private List<Integer> pageSizeAndOffset(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return Arrays.asList(pageSize, offset);
    }

    private List<Long> getFavorites() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Long> favorites = Collections.emptyList();
        if (!email.equalsIgnoreCase("anonymousUser")) {
            User user = userRepository.getUserByEmail(email)
                    .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email)));

            favorites = jdbcTemplate.queryForList(
                    "SELECT uf.favorite FROM user_favorite uf WHERE uf.user_id = ?",
                    Long.class,
                    user.getId());
        }
        return favorites;
    }

    private List<Long> getComparison() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Long> comparisons = Collections.emptyList();
        if (!email.equalsIgnoreCase("anonymousUser")) {
            User user = userRepository.getUserByEmail(email)
                    .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email)));

            comparisons = jdbcTemplate.queryForList(
                    "SELECT uc.comparison FROM user_comparison uc WHERE uc.user_id = ?",
                    Long.class,
                    user.getId());
        }
        return comparisons;
    }


    @Override
    public MainPagePaginationResponse getNewProducts(int page, int pageSize) {
        log.info("Getting all the new products!");
        return getProductsByQuery(getSql(), page, pageSize);
    }

    private static String getSql() {
        String sql = """
                SELECT DISTINCT sp.id                           AS id,
                                b.name                          AS name,
                                p.name                          AS prodName,
                                sp.price                        AS price,
                                sp.quantity                     AS quantity,
                                sp.code_color                   AS color,
                                d.sale                          AS discount,
                                cat.title                       AS catTitle,
                                sc.title                        AS subCatTitle,
                                p.created_at                    AS createdAt,
                                sp.rating                       AS rating,
                                COALESCE(
                                        (SELECT spi.images
                                         FROM sub_product_images spi
                                         WHERE spi.sub_product_id = sp.id
                                         LIMIT 1), ' ')         AS image,
                                COALESCE(rev.countOfReviews, 0) AS countOfReviews
                FROM sub_products sp
                         JOIN products p ON p.id = sp.product_id
                         JOIN brands b ON b.id = p.brand_id
                         JOIN sub_product_images spi ON sp.id = spi.sub_product_id
                         JOIN discounts d ON sp.id = d.sub_product_id
                         JOIN categories cat ON cat.id = p.category_id
                         JOIN sub_categories sc ON sc.id = p.sub_category_id
                         JOIN reviews r ON sp.id = r.sub_product_id
                         LEFT JOIN (SELECT sub_product_id, COUNT(id) AS countOfReviews
                                    FROM reviews
                                    GROUP BY sub_product_id) rev ON sp.id = rev.sub_product_id
                WHERE p.created_at BETWEEN (CURRENT_DATE - INTERVAL '1 month') AND CURRENT_DATE
                ORDER BY sp.id;
                """;
        sql += " LIMIT ? OFFSET ?";
        return sql;
    }

    @Override
    public MainPagePaginationResponse getRecommendedProducts(int page, int pageSize) {
        String sql = """
                SELECT DISTINCT sp.id                           AS id,
                                b.name                          AS name,
                                p.name                          AS prodName,
                                sp.price                        AS price,
                                sp.quantity                     AS quantity,
                                sp.code_color                   AS color,
                                d.sale                          AS discount,
                                cat.title                       AS catTitle,
                                sc.title                        AS subCatTitle,
                                p.created_at                    AS createdAt,
                                sp.rating                       AS rating,
                                COALESCE(
                                        (SELECT spi.images
                                         FROM sub_product_images spi
                                         WHERE spi.sub_product_id = sp.id
                                         LIMIT 1), ' ')         AS image,
                                COALESCE(rev.countOfReviews, 0) AS countOfReviews
                FROM sub_products sp
                         JOIN products p ON p.id = sp.product_id
                         JOIN brands b ON b.id = p.brand_id
                         JOIN sub_product_images spi ON sp.id = spi.sub_product_id
                         JOIN discounts d ON sp.id = d.sub_product_id
                         JOIN categories cat ON cat.id = p.category_id
                         JOIN sub_categories sc ON sc.id = p.sub_category_id
                         JOIN reviews r ON sp.id = r.sub_product_id
                         LEFT JOIN (SELECT sub_product_id, COUNT(id) AS countOfReviews
                                    FROM reviews
                                    GROUP BY sub_product_id) rev ON sp.id = rev.sub_product_id
                WHERE sp.rating > 4
                ORDER BY sp.id;
                """;
        return getProductsByQuery(sql, page, pageSize);
    }


    @Override
    public MainPagePaginationResponse getAllDiscountProducts(int page, int pageSize) {
        String sql = """
                SELECT DISTINCT sp.id                           AS id,
                                b.name                          AS name,
                                p.name                          AS prodName,
                                sp.price                        AS price,
                                sp.quantity                     AS quantity,
                                sp.code_color                   AS color,
                                d.sale                          AS discount,
                                cat.title                       AS catTitle,
                                sc.title                        AS subCatTitle,
                                p.created_at                    AS createdAt,
                                sp.rating                       AS rating,
                                COALESCE(
                                        (SELECT spi.images
                                         FROM sub_product_images spi
                                         WHERE spi.sub_product_id = sp.id
                                         LIMIT 1), ' ')         AS image,
                                COALESCE(rev.countOfReviews, 0) AS countOfReviews
                FROM sub_products sp
                         JOIN products p ON p.id = sp.product_id
                         JOIN brands b ON b.id = p.brand_id
                         JOIN sub_product_images spi ON sp.id = spi.sub_product_id
                         JOIN discounts d ON sp.id = d.sub_product_id
                         JOIN categories cat ON cat.id = p.category_id
                         JOIN sub_categories sc ON sc.id = p.sub_category_id
                         JOIN reviews r ON sp.id = r.sub_product_id
                         LEFT JOIN (SELECT sub_product_id, COUNT(id) AS countOfReviews
                                    FROM reviews
                                    GROUP BY sub_product_id) rev ON sp.id = rev.sub_product_id
                WHERE d.sale > 0
                ORDER BY sp.id;
                """;
        return getProductsByQuery(sql, page, pageSize);
    }

    private MainPagePaginationResponse getProductsByQuery(String sql, int page, int pageSize) {
        List<SubProductMainPageResponse> products = jdbcTemplate.query(
                sql,
                (resultSet, i) -> SubProductMainPageResponse.builder()
                        .subProductId(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .prodName(resultSet.getString("prodName"))
                        .quantity(resultSet.getInt("quantity"))
                        .rating(resultSet.getDouble("rating"))
                        .price(resultSet.getBigDecimal("price"))
                        .color(resultSet.getString("color"))
                        .discount(resultSet.getInt("discount"))
                        .image(resultSet.getString("image"))
                        .createdAt(resultSet.getDate("createdAt").toLocalDate())
                        .countOfReviews(resultSet.getInt("countOfReviews"))
                        .subCatTitle(resultSet.getString("subCatTitle"))
                        .catTitle(resultSet.getString("catTitle"))
                        .build(),
                pageSizeAndOffset(page, pageSize).get(0),
                pageSizeAndOffset(page, pageSize).get(1));

        List<Long> favorites = getFavorites();

        for (SubProductMainPageResponse s : products) {
            s.setFavorite(favorites.contains(s.getSubProductId()));
        }

        List<Long> comparisons = getComparison();

        for (SubProductMainPageResponse s : products) {
            s.setComparison(comparisons.contains(s.getSubProductId()));
        }

        return MainPagePaginationResponse.builder()
                .subProductMainPageResponses(products)
                .page(page)
                .pageSize(pageSize)
                .build();
    }

}
