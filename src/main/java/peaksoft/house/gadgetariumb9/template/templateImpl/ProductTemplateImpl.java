package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.product.AllInformationProduct;
import peaksoft.house.gadgetariumb9.dto.response.product.ProductUserAndAdminResponse;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewResponse;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import peaksoft.house.gadgetariumb9.template.ProductTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductTemplateImpl implements ProductTemplate {

    private final UserRepository userRepository;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ProductUserAndAdminResponse getByProductId(Long productId, String color) {

        log.info("Get product by id");

        String colourSql = "SELECT sp.code_color AS colours FROM sub_products sp JOIN products p on sp.product_id = p.id JOIN categories c on c.id = p.category_id JOIN brands b on b.id = p.brand_id WHERE p.id = ?";
        List<String> colours = jdbcTemplate.query(colourSql, (rs, i) -> rs.getString("colours"),
                productId);

        User user;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!email.equalsIgnoreCase("anonymousUser")) {
            user = userRepository.getUserByEmail(email).orElseThrow(
                    () -> {
                        log.error("User with id: " + email + " is not found");
                        return new NotFoundException("Product with id: " + email + " is not found");
                    });
        } else {
            user = null;
        }

        boolean isFavorite;

        if (user != null) {
            String favoriteSql = "SELECT 1 FROM user_favorite uf " +
                    "WHERE uf.user_id = ? AND uf.favorite IN (" +
                    "    SELECT sp.id FROM sub_products sp " +
                    "    JOIN products p ON sp.product_id = p.id " +
                    "    WHERE p.id = ? AND sp.code_color = ?" +
                    ")";
            isFavorite = jdbcTemplate.query(favoriteSql, (rs, i) -> true, user.getId(), productId,
                    !color.isBlank() ? color : colours.get(0)).stream().findFirst().orElse(false);
        } else {
            isFavorite = false;
        }

        if (!color.isBlank() && !colours.contains(color)) {
            log.error(String.format("Product with colour - %s is not found!", color));
            throw new NotFoundException(String.format("Product with colour - %s is not found!", color));
        }

        String sql = """
                SELECT p.id                                                            AS product_id,
                       sp.id                                                           AS sub_product_id,
                       b.name                                                          AS brand_name,
                       c.title                                                         AS category,
                       p.name                                                          AS name,
                       sp.quantity                                                     AS quantity,
                       sp.price                                                        AS price,
                       sp.rating                                                       AS rating,
                       (SELECT COUNT(r.id) FROM reviews r
                       JOIN sub_products sp ON sp.id = r.sub_product_id
                       JOIN products p on sp.product_id = p.id
                       WHERE p.id = ? and sp.code_color = ?)                           AS count_of_reviews,
                       sp.article_number                                               AS article_number,
                       d.sale                                                          AS discount,
                       sp.screen_resolution                                            AS screen_resolution,
                       sp.code_color                                                   AS color,
                       p.data_of_issue                                                 AS data_of_issue,
                       sp.rom                                                          AS rom,
                       p.guarantee                                                     AS guarantee,
                       p.description                                                   AS description,
                       p.video_link                                                    AS video_link
                FROM sub_products sp JOIN products p ON sp.product_id = p.id
                JOIN brands b ON b.id = p.brand_id
                JOIN categories c on c.id = p.category_id
                  LEFT JOIN discounts d ON sp.id = d.sub_product_id
                  LEFT JOIN reviews r ON sp.id = r.sub_product_id
                  LEFT JOIN user_favorite uf ON r.user_id = uf.user_id AND uf.user_id = ?
                WHERE p.id = ? AND sp.code_color = ?
                """;

        ProductUserAndAdminResponse response = new ProductUserAndAdminResponse();
        jdbcTemplate.query(sql, (rs, i) -> {
                    response.setProductId(rs.getLong("product_id"));
                    response.setSubProductId(rs.getLong("sub_product_id"));
                    response.setBrandName(rs.getString("brand_name"));
                    response.setCategory(rs.getString("category"));
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
                    response.setDescription(rs.getString("description"));
                    response.setVideoLink(rs.getString("video_link"));
                    response.setFavorite(isFavorite);
                    return response;
                },
                productId,
                !color.isBlank() ? color : colours.get(0),
                user != null ? user.getId() : null,
                productId,
                !color.isBlank() ? color : colours.get(0)
        );

        response.setColours(colours);

        String imageSql = """
                SELECT spi.images AS images FROM sub_product_images spi
                join sub_products sp on spi.sub_product_id = sp.id
                join products p on p.id = sp.product_id
                where p.id = ? and  sp.code_color = ?
                """;
        List<String> images = jdbcTemplate.query(imageSql, (rs, i) ->
                        rs.getString("images"),
                productId,
                !color.isBlank() ? color : colours.get(0)
        );

        response.setImages(images);

        String reviewSql = """
                SELECT DISTINCT r.id,
                                CONCAT(u.first_name, ' ', u.last_name) AS user_name,
                                u.image                                AS user_image,
                                r.user_id                              AS user_id,
                                r.grade                                AS grade,
                                r.comment                              AS comment,
                                r.reply_to_comment                     AS answer,
                                r.date_creat_ad                        AS date,
                                r.image_link                           AS image
                FROM reviews r
                         JOIN sub_products sp ON r.sub_product_id = sp.id
                         JOIN users u ON u.id = r.user_id
                         JOIN products p ON sp.product_id = p.id
                WHERE p.id = ? and sp.code_color = ?
                """;

        List<ReviewResponse> reviewResponses = jdbcTemplate.query(
                reviewSql, (rs, rowNum) -> {
                    ReviewResponse review = new ReviewResponse(
                            rs.getLong("id"),
                            rs.getString("user_name"),
                            rs.getString("user_image"),
                            rs.getInt("grade"),
                            rs.getString("comment"),
                            rs.getString("answer"),
                            rs.getString("date"),
                            rs.getString("image"));
                    review.setMy(user != null && user.getId().equals(getUserIdFromRS(rs)));
                    return review;
                },
                productId,
                !color.isBlank() ? color : colours.get(0));

        response.setReviews(reviewResponses);

        boolean inBasket;

        if (user != null) {
            String inBasketSql = """
                        SELECT 1 FROM baskets_sub_products bsp
                        JOIN baskets b ON b.id = bsp.baskets_id
                        WHERE b.user_id = ? AND bsp.sub_products_id IN
                        (SELECT sp.id FROM sub_products sp
                         JOIN products p ON sp.product_id = p.id
                         WHERE p.id = ? AND sp.code_color = ?)
                    """;
            inBasket = jdbcTemplate.query(inBasketSql, (rs, i) -> true, user.getId(), productId,
                    !color.isBlank() ? color : colours.get(0)).stream().findFirst().orElse(false);
        } else {
            inBasket = false;
        }

        response.setInBasket(inBasket);

        log.info("Product successfully get!");
        return response;
    }

    private Long getUserIdFromRS(ResultSet rs) throws SQLException {
        return rs.getLong("user_id");
    }

    @Override
    public AllInformationProduct getAllProductInformation(Long subProductId) {
        String sql = """
                SELECT
                    p.id AS product_id,
                    p.name AS product_name,
                    p.data_of_issue,
                    p.guarantee,
                    p.description,
                    p.pdf,
                    p.video_link,
                    sp.id AS sub_product_id,
                    (SELECT string_agg(spi.images,',')
                    FROM sub_product_images spi
                    WHERE spi.sub_product_id = sp.id) AS images,
                    sp.price,
                    sp.ram,
                    sp.rom,
                    sp.code_color,
                    sp.screen_resolution,
                    l.screen_size AS laptop_screen_size,
                    l.video_memory,
                    l.processor,
                    l.purpose,
                    ph.screen_size AS phone_screen_size,
                    ph.sim,
                    ph.battery_capacity,
                    ph.diagonal_screen,
                    sw.display_discount,
                    sw.waterproof,
                    sw.an_interface,
                    sw.gender,
                    sw.housing_material,
                    sw.hull_shape,
                    sw.material_bracelet,
                    c.title AS category_title,
                    sc.title AS sub_category_title,
                    b.name AS brand_title
                FROM products p
                         LEFT JOIN sub_products sp ON p.id = sp.product_id
                         LEFT JOIN laptops l ON sp.id = l.sub_product_id
                         LEFT JOIN phones ph ON sp.id = ph.sub_product_id
                         LEFT JOIN smart_watches sw ON sp.id = sw.sub_product_id
                         LEFT JOIN categories c ON p.category_id = c.id
                         LEFT JOIN sub_categories sc ON p.sub_category_id = sc.id
                         LEFT JOIN brands b on p.brand_id = b.id
                WHERE sp.id = ?
                """;
        return jdbcTemplate.queryForObject(sql, (rs, i) -> AllInformationProduct
                .builder()
                .productId(rs.getLong("product_id"))
                .productName(rs.getString("product_name"))
                .productDataOfIssue(rs.getDate("data_of_issue"))
                .productGuarantee(rs.getInt("guarantee"))
                .productDescription(rs.getString("description"))
                .productPdf(rs.getString("pdf"))
                .productVideoLink(rs.getString("video_link"))
                .supProductId(rs.getLong("sub_product_id"))
                .images(Arrays.asList(rs.getString("images").split(",")))
                .price(rs.getBigDecimal("price"))
                .ram(rs.getInt("ram"))
                .rom(rs.getInt("rom"))
                .codeColor(rs.getString("code_color"))
                .screenResolution(rs.getString("screen_resolution"))
                .laptopScreenSize(rs.getDouble("laptop_screen_size"))
                .videoMemory(rs.getInt("video_memory"))
                .processor(rs.getString("processor"))
                .purpose(rs.getString("purpose"))
                .phoneScreenSize(rs.getDouble("phone_screen_size"))
                .sim(rs.getInt("sim"))
                .batteryCapacity(rs.getString("battery_capacity"))
                .diagonalScreen(rs.getString("diagonal_screen"))
                .displayDiscount(rs.getDouble("display_discount"))
                .waterproof(rs.getBoolean("waterproof"))
                .anInterface(rs.getString("an_interface"))
                .gender(rs.getString("gender"))
                .housingMaterial(rs.getString("housing_material"))
                .hullShape(rs.getString("hull_shape"))
                .materialBracelet(rs.getString("material_bracelet"))
                .categoryTitle(rs.getString("category_title"))
                .subCategoryTitle(rs.getString("sub_category_title"))
                .brandTitle(rs.getString("brand_title"))
                .build(),
                subProductId);
    }

}