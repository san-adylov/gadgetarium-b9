package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.response.user.UserFavoritesResponse;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.template.FavoriteTemplate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteTemplateImpl implements FavoriteTemplate {

    private final JdbcTemplate jdbcTemplate;

    private final JwtService jwtService;

    @Override
    public List<SubProductResponse> getAllFavorite() {
        User user = jwtService.getAuthenticationUser();
        String query = """
                SELECT DISTINCT sp.id,
                b.name,
                    p.name AS prod_name,
                       sp.article_number,
                       sp.price,
                       sp.quantity,
                       sp.ram,
                       sp.rom,
                       sp.additional_features,
                       sp.code_color,
                       sp.screen_resolution,
                       d.sale,
                       COALESCE(
                       (SELECT spi.images
                        FROM sub_product_images spi
                        WHERE spi.sub_product_id = sp.id
                        LIMIT 1),' ') AS image
                FROM sub_products sp
                JOIN products p ON p.id = sp.product_id
                JOIN brands b ON b.id = p.brand_id
                JOIN sub_product_images spi ON sp.id = spi.sub_product_id
                JOIN discounts d ON spi.sub_product_id = d.sub_product_id
                JOIN user_favorite uf ON uf.favorite = sp.id
                JOIN users u ON uf.user_id = u.id
                WHERE u.id = ?
                     """;

        return jdbcTemplate.query(
                query,
                (rs, rowNum) -> new SubProductResponse(
                        rs.getString("name"),
                        rs.getString("prod_name"),
                        rs.getLong("id"),
                        rs.getInt("ram"),
                        rs.getString("screen_resolution"),
                        rs.getInt("rom"),
                        rs.getString("additional_features"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        rs.getString("code_color"),
                        rs.getLong("article_number"),
                        rs.getString("image"),
                        rs.getInt("sale")),
                user.getId());
    }

    @Override
    public List<UserFavoritesResponse> getAllFavoriteByUserId(Long userId) {
        String sql = """
                SELECT s.id,
                       p.name,
                       s.rating,
                       (SELECT COUNT(r.id) FROM reviews r WHERE r.sub_product_id = s.id)                       AS quantity,
                       s.price,
                       (SELECT spi.images FROM sub_product_images spi WHERE spi.sub_product_id = s.id LIMIT 1) AS image
                FROM sub_products s
                         JOIN products p ON s.product_id = p.id
                         JOIN user_favorite us ON us.favorite = s.id
                         JOIN users u ON us.user_id = u.id
                WHERE u.id = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> UserFavoritesResponse
                        .builder()
                        .subProductId(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .rating(rs.getDouble("rating"))
                        .countOfReview(rs.getInt("quantity"))
                        .price(rs.getBigDecimal("price"))
                        .image(rs.getString("image"))
                        .build(),
                userId);
    }

}

