package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.brand.BrandResponse;
import peaksoft.house.gadgetariumb9.dto.response.category.CategoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.AdminSearchResponse;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductResponse;
import peaksoft.house.gadgetariumb9.template.GlobalSearchTemplate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GlobalSearchTemplateImpl implements GlobalSearchTemplate {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public GlobalSearchResponse globalSearch(String keyword) {

        String sql = """
                    
                SELECT b.id, b.name, b.image FROM brands b WHERE b.name ILIKE (concat('%' || ? || '%'))
                                                                                            """;
        List<BrandResponse> brandResponses = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> BrandResponse.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .image(rs.getString("image"))
                        .build(),
                keyword
        );

        String sql2 = """
                 SELECT c.id, c.title
                FROM categories c
                WHERE c.title ILIKE (concat('%' || ? || '%'))
                           """;
        List<CategoryResponse> categoryResponses = jdbcTemplate.query(
                sql2,
                (rs, rowNum) -> CategoryResponse.builder()
                        .categoryId(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .build(),
                keyword
        );
        String sql3 = """
                SELECT p.name,
                       b.name                as brandName,
                       s.id                  as subProductId,
                       s.ram,
                       s.screen_resolution   as screenResolution,
                       s.rom,
                       s.additional_features as additionalFeatures,
                       s.price,
                       s.quantity,
                       s.code_color,
                       (SELECT spi.images
                        FROM sub_product_images spi
                        WHERE spi.sub_product_id = s.id
                        LIMIT 1)             as image,
                       s.article_number
                FROM sub_products s
                         JOIN products p on p.id = s.product_id
                         JOIN brands b on b.id = p.brand_id
                WHERE CAST(s.code_color AS TEXT) ILIKE (concat('%' || ? || '%'))
                   OR CAST(s.price AS TEXT) ILIKE (concat('%' || ? || '%'))
                   OR CAST(s.article_number AS TEXT) ILIKE (concat('%' || ? || '%'))                                                                       \s
                 """;

        List<SubProductResponse> subProductResponses = jdbcTemplate.query(
                sql3,
                (rs, rowNum) -> SubProductResponse.builder()
                        .name(rs.getString("name"))
                        .brandName(rs.getString("brandName"))
                        .subProductId(rs.getLong("subProductId"))
                        .ram(rs.getInt("ram"))
                        .screenResolution(rs.getString("screenResolution"))
                        .rom(rs.getInt("rom"))
                        .additionalFeatures(rs.getString("additionalFeatures"))
                        .price(rs.getBigDecimal("price"))
                        .quantity(rs.getString("quantity"))
                        .codeColor(rs.getString("code_color"))
                        .image(rs.getString("image"))
                        .articleNumber(rs.getLong("article_number"))
                        .build(),
                keyword, keyword, keyword
        );

        return GlobalSearchResponse.builder()
                .brandList(brandResponses)
                .categoryList(categoryResponses)
                .subProductResponses(subProductResponses)
                .build();
    }

    @Override
    public List<AdminSearchResponse> adminSearch(String keyword) {

        String sql = """        
            SELECT p.id                                                              as product_id,
                   (SELECT spi.images
                    FROM sub_product_images spi
                    WHERE spi.sub_product_id = sp.id
                    LIMIT 1)                                                         as images,
                   sp.article_number                                                 as article_number,
                   p.name                                                            as name,
                   p.data_of_issue                                                   as data_of_issue,
                   sp.quantity                                                       as quantity,
                   concat(sp.price,', ',d.sale)                                      as price_and_sale,
                   SUM(sp.price * (1 - d.sale / 100.0))                              AS current_price 
            FROM products p
            JOIN sub_products sp on p.id = sp.product_id
            LEFT JOIN discounts d on sp.id = d.sub_product_id
            WHERE CAST(sp.article_number AS TEXT) ILIKE (concat('%' || ? || '%'))
                     OR CAST(p.name AS TEXT) ILIKE (concat('%' || ? || '%'))
                     OR CAST(p.data_of_issue AS TEXT) ILIKE (concat('%' || ? || '%'))  
                     OR CAST(sp.price AS TEXT) ILIKE (concat('%' || ? || '%'))
            GROUP BY p.id,sp.id, sp.article_number, p.name, p.data_of_issue, sp.quantity, sp.price, d.sale order by sp.price desc 
            """;

        return jdbcTemplate.query(sql,
            (rs, rowNum) -> {
                AdminSearchResponse response = new AdminSearchResponse();
                response.setProductId(rs.getLong("product_id"));
                response.setImages(rs.getString("images"));
                response.setArticleNumber(rs.getInt("article_number"));
                response.setName(rs.getString("name"));
                response.setDataOfIssue(rs.getDate("data_of_issue").toLocalDate());
                response.setQuantity(rs.getInt("quantity"));
                response.setPriceAndSale(rs.getString("price_and_sale"));
                response.setCurrentPrice(rs.getBigDecimal("current_price"));
                return response;
            },keyword, keyword, keyword, keyword
        );
    }

}
