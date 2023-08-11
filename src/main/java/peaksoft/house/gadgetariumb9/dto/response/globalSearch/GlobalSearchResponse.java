package peaksoft.house.gadgetariumb9.dto.response.globalSearch;

import lombok.*;
import peaksoft.house.gadgetariumb9.dto.response.brand.BrandResponse;
import peaksoft.house.gadgetariumb9.dto.response.category.CategoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductResponse;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalSearchResponse {

    private List<BrandResponse> brandList;

    private List<CategoryResponse>categoryList;

    private List<SubProductResponse> subProductResponses;


}
