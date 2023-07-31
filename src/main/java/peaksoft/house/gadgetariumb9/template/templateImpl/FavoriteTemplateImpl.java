package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductResponse;
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
        User user = jwtService.getAuthentication();
        String query = """
                SELECT sp.id,
                b.name,
                    p.name as prodNamae,
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
                JOIN products p on p.id = sp.product_id
                JOIN brands b on b.id = p.brand_id
                JOIN sub_product_images spi ON sp.id = spi.sub_product_id
                JOIN discounts d on spi.sub_product_id = d.sub_product_id
                JOIN user_favorite uf ON uf.favorite = sp.id
                JOIN users u ON uf.user_id = u.id
                WHERE u.id = ?
                     """;

        return jdbcTemplate.query(
                query,
                (rs, rowNum) -> new SubProductResponse(
                        rs.getString("name"),
                        rs.getString("prodNamae"),
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
}

