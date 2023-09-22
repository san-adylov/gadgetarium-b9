package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.response.review.AdminAllReviews;
import peaksoft.house.gadgetariumb9.dto.response.review.AdminReviewPagination;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewGradeInfo;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewPagination;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewResponse;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewsRatings;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.template.ReviewTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewTemplateImpl implements ReviewTemplate {

    private final JdbcTemplate jdbcTemplate;

    private final JwtService jwtService;

    @Override
    public ReviewPagination getAll(Long subProductId, int pageSize, int numberPage) {
        log.info("Get all comments");
        String sql = """
                        SELECT DISTINCT
                          r.id,
                          CONCAT(u.first_name, ' ', u.last_name) AS user_name,
                          u.image                                AS user_image,
                          r.grade                                AS grade,
                          r.comment                              AS comment,
                          r.reply_to_comment                     AS answer,
                          r.date_creat_ad                        AS date,
                          r.image_link                           AS image,
                          u.id                                   AS user_id
          FROM reviews r
                   JOIN sub_products sp ON r.sub_product_id = sp.id
                   JOIN users u ON u.id = r.user_id
                   JOIN products p ON sp.product_id = p.id
          WHERE sp.id = ?
          ORDER BY r.date_creat_ad DESC
          LIMIT ? OFFSET ?
          """;

        int offset = (numberPage - 1) * pageSize;

        User user = jwtService.getAuthenticationUser();

        List<ReviewResponse> reviewResponses = jdbcTemplate.query(
                sql, (rs, rowNum) -> {
                    ReviewResponse response = new ReviewResponse(
                            rs.getLong("id"),
                            rs.getString("user_name"),
                            rs.getString("user_image"),
                            rs.getInt("grade"),
                            rs.getString("comment"),
                            rs.getString("answer"),
                            rs.getString("date"),
                            rs.getString("image"));
                    response.setMy(user != null && user.getId().equals(getUserIdFromResultSet(rs)));
                    return response;
                },
                subProductId, pageSize, offset);

        return new ReviewPagination(reviewResponses, pageSize, numberPage);
    }

    private Long getUserIdFromResultSet(ResultSet rs) throws SQLException {
        return rs.getLong("user_id");
    }

    @Override
    public ReviewGradeInfo getFeedback(Long subProductId) {
        String sql = """
                  SELECT
                    (SELECT count(*) from reviews r where r.sub_product_id = sp.id and grade = 5) as five,
                    (select count(*) from reviews r where r.sub_product_id = sp.id and grade = 4) as four,
                    (select count(*) from reviews r where r.sub_product_id = sp.id and grade = 3) as three,
                    (select count(*) from reviews r where r.sub_product_id = sp.id and grade = 2) as two,
                    (select count(*) from reviews r where r.sub_product_id = sp.id and grade = 1) as one,
                    (select avg(r.grade) from reviews r where r.sub_product_id = sp.id) as rating,
                    (select count(*) from reviews r where r.sub_product_id = sp.id) as total_reviews
                from sub_products sp where id = ?;
                """;
        return jdbcTemplate.query(sql, (rs, i) -> {
                    ReviewGradeInfo rw = new ReviewGradeInfo();
                    rw.setFive(rs.getInt("five"));
                    rw.setFour(rs.getInt("four"));
                    rw.setThree(rs.getInt("three"));
                    rw.setTwo(rs.getInt("two"));
                    rw.setOne(rs.getInt("one"));
                    rw.setRating(rs.getDouble("rating"));
                    rw.setTotalReviews(rs.getInt("total_reviews"));
                    return rw;
                }, subProductId).stream().findAny()
                .orElseThrow(() -> new NotFoundException("The index not found!"));
    }

  @Override
  public AdminReviewPagination getAllReviewsForAdmin(String filter, int pageSize, int pageNumber) {

    String sql = """
            SELECT DISTINCT
              r.id,
              (SELECT spi.images
              FROM sub_product_images spi
              WHERE spi.sub_product_id = sp.id
              LIMIT 1)                               AS product_image,
              p.name                                 AS product_name,
              b.name                                 AS brand_name,
              sp.article_number                      AS article_number,
              CONCAT(u.first_name, ' ', u.last_name) AS user_name,
              u.email                                AS user_email,
              u.image                                AS user_image,
              r.grade                                AS grade,
              r.comment                              AS comment,
              r.reply_to_comment                     AS answer,
              r.date_creat_ad                        AS date,
              r.image_link                           AS image,
              r.is_viewed                            AS is_view
        FROM reviews r
           JOIN sub_products sp ON r.sub_product_id = sp.id
           JOIN users u ON u.id = r.user_id
           JOIN products p ON sp.product_id = p.id
           JOIN brands b on b.id = p.brand_id
        """;

    if ("Неотвеченные".equals(filter)) {
      sql += " AND r.reply_to_comment IS NULL";
    } else if ("Отвеченные".equals(filter)) {
      sql += " AND r.reply_to_comment IS NOT NULL";
    }

    sql += " ORDER BY r.date_creat_ad DESC";

    int offset = (pageNumber - 1) * pageSize;
    sql += " LIMIT ? OFFSET ?";

    List<AdminAllReviews> reviewResponses = jdbcTemplate.query(
        sql, (rs, rowNum) -> new AdminAllReviews(
            rs.getLong("id"),
            rs.getString("product_image"),
            rs.getString("product_name"),
            rs.getString("brand_name"),
            rs.getInt("article_number"),
            rs.getString("user_name"),
            rs.getString("user_email"),
            rs.getString("user_image"),
            rs.getInt("grade"),
            rs.getString("comment"),
            rs.getString("answer"),
            rs.getString("date"),
            rs.getString("image"),
            rs.getBoolean("is_view")),
        pageSize, offset);

    String countSql = """
    SELECT COUNT(*) AS total_count
    FROM reviews r
    JOIN sub_products sp ON r.sub_product_id = sp.id
    JOIN users u ON u.id = r.user_id
    JOIN products p ON sp.product_id = p.id
    JOIN brands b on b.id = p.brand_id
    """;

    if ("Неотвеченные".equals(filter)) {
      countSql += " AND r.reply_to_comment IS NULL";
    } else if ("Отвеченные".equals(filter)) {
      countSql += " AND r.reply_to_comment IS NOT NULL";
    }

    Integer totalCount = jdbcTemplate.queryForObject(countSql, Integer.class);
    int totalCountValue = totalCount != null ? totalCount : 0;

    return new AdminReviewPagination(reviewResponses, pageSize, pageNumber,totalCountValue);
  }

  @Override
  public ReviewsRatings getAllRatings() {
    String sql = """
        SELECT
            SUM(CASE WHEN grade = 5 THEN 1 ELSE 0 END) as five,
            SUM(CASE WHEN grade = 4 THEN 1 ELSE 0 END) as four,
            SUM(CASE WHEN grade = 3 THEN 1 ELSE 0 END) as three,
            SUM(CASE WHEN grade = 2 THEN 1 ELSE 0 END) as two,
            SUM(CASE WHEN grade = 1 THEN 1 ELSE 0 END) as one,
            COUNT(*) as total_reviews
        FROM reviews;
    """;

    return jdbcTemplate.queryForObject(sql, (rs, i) -> {
      ReviewsRatings rev = new ReviewsRatings();
      rev.setFive(rs.getInt("five"));
      rev.setFour(rs.getInt("four"));
      rev.setThree(rs.getInt("three"));
      rev.setTwo(rs.getInt("two"));
      rev.setOne(rs.getInt("one"));
      rev.setTotalReviews(rs.getInt("total_reviews"));
      return rev;
    });
  }
}
