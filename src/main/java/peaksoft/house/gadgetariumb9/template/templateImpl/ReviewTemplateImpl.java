package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewGradeInfo;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewPagination;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewResponse;
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
                   JOIN products p ON p.id = r.sub_product_id
                   JOIN sub_products sp ON r.sub_product_id = sp.product_id
                   JOIN users u ON u.id = r.user_id
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
}
