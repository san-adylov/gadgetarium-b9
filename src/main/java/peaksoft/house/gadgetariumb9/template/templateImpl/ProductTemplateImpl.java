package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.product.ProductUserAndAdminResponse;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewResponse;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import peaksoft.house.gadgetariumb9.template.ProductTemplate;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductTemplateImpl implements ProductTemplate {

    private final UserRepository userRepository;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ProductUserAndAdminResponse getByProductId(Long subProductId, String color) {

        log.info("Get product by id");

        String colourSql = "SELECT sp.code_color AS colours FROM sub_products sp WHERE sp.id = ?";
        List<String> colours = jdbcTemplate.query(colourSql, (rs, i) -> rs.getString("colours"),
                subProductId);

        User user = null;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!email.equalsIgnoreCase("anonymousUser")) {
            user = userRepository.getUserByEmail(email).orElseThrow(
                    () -> {
                        log.error("User with id: " + email + " is not found");
                        return new NotFoundException("Product with id: " + email + " is not found");
                    });
        }

        String sql = """
                SELECT p.id                                                            AS product_id,
                       sp.id                                                           AS sub_product_id,
                       b.name                                                          AS brand_name,
                       p.name                                                          AS name,
                       sp.quantity                                                     AS quantity,
                       sp.price                                                        AS price,
                       sp.rating                                                       AS rating,
                       (SELECT COUNT(r.id) FROM reviews r JOIN sub_products sp ON sp.id = r.sub_product_id
                           WHERE sp.id = ?)                                             AS count_of_reviews,
                       sp.article_number                                               AS article_number,
                       d.sale                                                          AS discount,
                       sp.screen_resolution                                            AS screen_resolution,
                       sp.code_color                                                   AS color,
                       p.data_of_issue                                                 AS data_of_issue,
                       sp.rom                                                          AS rom,
                       p.guarantee                                                     AS guarantee,
                       CASE WHEN uf IS NULL THEN FALSE OR TRUE END                     AS isFavorite,
                       p.description                                                   AS description,
                       p.video_link                                                    AS video_link
                FROM sub_products sp JOIN products p ON sp.product_id = p.id
                JOIN brands b ON b.id = p.brand_id
                LEFT JOIN discounts d ON sp.id = d.sub_product_id
                LEFT JOIN reviews r ON sp.id = r.sub_product_id
                LEFT JOIN user_favorite uf ON r.user_id = uf.user_id AND uf.user_id = ?
                WHERE sp.id = ? AND sp.code_color = ?
                """;

        ProductUserAndAdminResponse response = new ProductUserAndAdminResponse();
        jdbcTemplate.query(sql, (rs, i) -> {
                    response.setProductId(rs.getLong("product_id"));
                    response.setSubProductId(rs.getLong("sub_product_id"));
                    response.setBrandName(rs.getString("brand_name"));
                    response.setName(rs.getString("name"));
                    response.setQuantity(rs.getInt("quantity"));
                    response.setPrice(rs.getBigDecimal("price"));
                    response.setRating(rs.getDouble("rating"));
                    response.setCountOfReviews(rs.getInt("count_of_reviews"));
                    response.setArticleNumber(rs.getInt("article_number"));
                    response.setDiscountOfProduct(rs.getInt("discount"));
                    response.setScreenResolution(rs.getString("screen_resolution"));
                    response.setColor(rs.getString("color"));
                    response.setDataOfIssue(rs.getString("data_of_issue"));
                    response.setRom(rs.getInt("rom"));
                    response.setGuarantee(rs.getInt("guarantee"));
                    response.setFavorite(rs.getBoolean("isFavorite"));
                    response.setDescription(rs.getString("description"));
                    response.setVideoLink(rs.getString("video_link"));
                    return response;
                },
                subProductId,
                user != null ? user.getId() : null,
                subProductId,
                !color.isBlank() ? color : colours.get(0)
        );

        response.setColours(colours);

        String imageSql = """
                SELECT spi.images AS images FROM sub_product_images spi
                join sub_products sp on spi.sub_product_id = sp.id
                where sp.id = ? and  sp.code_color = ?
                """;
        List<String> images = jdbcTemplate.query(imageSql, (rs, i) ->
                        rs.getString("images"),
                subProductId,
                !color.isBlank() ? color : colours.get(0)
        );

        response.setImages(images);

        String reviewSql = """
                SELECT DISTINCT CONCAT(u.first_name, ' ', u.last_name)                        AS user_name,
                                u.image                                                       AS user_image,
                                r.grade                                                       AS grade,
                                r.comment                                                     AS comment,
                                r.reply_to_comment                                            AS answer,
                                r.date_creat_ad                                               AS date,
                                r.image_link                                                  AS image
                FROM reviews r
                         JOIN products p ON p.id = r.sub_product_id
                         JOIN sub_products sp ON r.sub_product_id = sp.product_id
                         JOIN users u ON u.id = r.user_id
                         WHERE sp.id = ?
                """;

        List<ReviewResponse> reviewResponses = jdbcTemplate.query(
                reviewSql, (rs, rowNum) -> new ReviewResponse(
                        rs.getString("user_name"),
                        rs.getString("user_image"),
                        rs.getInt("grade"),
                        rs.getString("comment"),
                        rs.getString("answer"),
                        rs.getString("date"),
                        rs.getString("image")),
                subProductId);

        response.setReviews(reviewResponses);

        log.info("Product successfully get!");
        return response;
    }
}