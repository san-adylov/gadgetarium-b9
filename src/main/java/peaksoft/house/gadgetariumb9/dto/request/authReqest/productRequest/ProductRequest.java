package peaksoft.house.gadgetariumb9.dto.request.authReqest.productRequest;

import lombok.*;
import java.time.ZonedDateTime;

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
