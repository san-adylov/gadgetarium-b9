package peaksoft.house.gadgetariumb9.dto.request.product;

import lombok.*;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductRequest;
import java.time.LocalDate;
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

    private LocalDate dateOfIssue;

    private List<SubProductRequest> subProductRequests;

    private String videoLink;

    private String pdf;

    private String description;

}
