package peaksoft.house.gadgetariumb9.dto.request.authReqest.productRequest;

import lombok.*;
import peaksoft.house.gadgetariumb9.entities.Brand;
import peaksoft.house.gadgetariumb9.entities.Category;
import peaksoft.house.gadgetariumb9.entities.SubCategory;
import peaksoft.house.gadgetariumb9.entities.SubProduct;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    private Long categoryId;

    private Long subCategoryId;

    private Long brandId;

    private String name;

    private int guarantee;

    private ZonedDateTime dateOfIssue;

}
