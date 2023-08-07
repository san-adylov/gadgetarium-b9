package peaksoft.house.gadgetariumb9.template.templateImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.brand.BrandResponse;
import peaksoft.house.gadgetariumb9.dto.response.category.CategoryResponse;
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
   OR CAST(s.article_number AS TEXT) ILIKE (concat('%' || ? || '%'))
                                                                        
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

}
