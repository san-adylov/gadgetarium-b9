package peaksoft.house.gadgetariumb9.dto.response.productResponse;

import lombok.*;
import java.time.ZonedDateTime;

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

    private  int guarantee;

    private String name;

    private ZonedDateTime dateOfIssue;
}
