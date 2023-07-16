package peaksoft.house.gadgetariumb9.dto.response.productResponse;

import lombok.*;
import peaksoft.house.gadgetariumb9.entities.Brand;
import peaksoft.house.gadgetariumb9.entities.Category;
import peaksoft.house.gadgetariumb9.entities.SubProduct;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long id;

    private Long categoryId;

    private Long subCategoryId;

    private Long brandId;

    private int guarantee;

    private String name;

    private ZonedDateTime dateOfIssue;
}
