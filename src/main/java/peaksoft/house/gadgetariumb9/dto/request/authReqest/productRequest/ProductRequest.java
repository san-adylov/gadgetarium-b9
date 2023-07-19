package peaksoft.house.gadgetariumb9.dto.request.authReqest.productRequest;

import lombok.*;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.subProduct.SubProductRequest;

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

    private List<SubProductRequest> subProductRequests;

}
