package peaksoft.house.gadgetariumb9.dto.response.brand.subProductResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
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
