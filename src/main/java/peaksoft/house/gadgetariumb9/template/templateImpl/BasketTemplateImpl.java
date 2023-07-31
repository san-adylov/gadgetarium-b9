package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.response.basket.BasketInfographicResponse;
import peaksoft.house.gadgetariumb9.dto.response.basket.BasketResponse;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.template.BasketTemplate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BasketTemplateImpl implements BasketTemplate {

    private final JdbcTemplate jdbcTemplate;

    private final JwtService jwtService;

    @Override
    public List<BasketResponse> getAllByProductsFromTheBasket() {
        User user = jwtService.getAuthentication();
        String sql = """
                 SELECT sp.id,
                        (SELECT spi.images
                         FROM sub_product_images spi
                         WHERE spi.sub_product_id = sp.id
                         LIMIT 1) AS image,
                        p.name,
                        sp.rating,
                        COALESCE((SELECT COUNT(DISTINCT r.id) FROM reviews r WHERE r.sub_product_id = sp.id), 0) AS number_of_reviews,
                        sp.quantity,
                        sp.article_number,
                        SUM(sp.price) AS total_price,
                        COUNT(sp.id) AS the_number_of_orders
                FROM sub_products sp
                         JOIN baskets_sub_products bsp ON sp.id = bsp.sub_products_id
                         JOIN baskets b ON bsp.baskets_id = b.id
                         JOIN products p ON sp.product_id = p.id
                         JOIN reviews r ON sp.id = r.sub_product_id
                         JOIN users u ON b.user_id = u.id
                WHERE u.id = ?
                GROUP BY sp.id, p.name, (SELECT spi.images FROM sub_product_images spi WHERE spi.sub_product_id = sp.id LIMIT 1),
                         sp.rating, sp.quantity, sp.
                   article_number, sp.price;
                   """;
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        BasketResponse
                                .builder()
                                .subProductId(rs.getLong("id"))
                                .image(rs.getString("image"))
                                .title(rs.getString("name"))
                                .rating(rs.getInt("rating"))
                                .numberOfReviews(rs.getInt("number_of_reviews"))
                                .quantity(rs.getInt("quantity"))
                                .articleNumber(rs.getInt("article_number"))
                                .price(rs.getBigDecimal("total_price"))
                                .theNumberOfOrders(rs.getInt("the_number_of_orders"))
                                .build(),
                user.getId()
        );
    }

    @Override
    public BasketInfographicResponse getInfographic() {
        User user = jwtService.getAuthentication();
        String sql = """             
                   SELECT COUNT(bsp.sub_products_id)                            AS quantity,
                       SUM(sp.price * COALESCE(d.sale, 100)) / 100              AS sale,
                       SUM(sp.price)                                            AS price,
                       SUM(sp.price - (sp.price * COALESCE(d.sale, 100)) / 100) AS total_sum
                FROM baskets b
                         JOIN users u ON b.user_id = u.id
                         JOIN baskets_sub_products bsp ON b.id = bsp.baskets_id
                         JOIN sub_products sp ON bsp.sub_products_id = sp.id
                         LEFT JOIN discounts d ON sp.id = d.sub_product_id
                WHERE u.id = ?
                   """;
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> BasketInfographicResponse
                        .builder()
                        .quantitySubProducts(rs.getInt("quantity"))
                        .totalDiscount(rs.getInt("sale"))
                        .totalPrice(rs.getBigDecimal("price"))
                        .toPay(rs.getBigDecimal("total_sum"))
                        .build(),
                user.getId()
        );
    }
}
