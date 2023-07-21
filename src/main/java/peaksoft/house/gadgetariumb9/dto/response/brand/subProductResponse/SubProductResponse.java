package peaksoft.house.gadgetariumb9.dto.response.brand.subProductResponse;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubProductResponse {
    private Long id;

    private int ram;

    private String screenResolution;

    private int rom;

    private String additionalFeatures;

    private BigDecimal price;

    private int quantity;

    private String codeColor;

    private List<String> images;

    private int articleNumber;
}
