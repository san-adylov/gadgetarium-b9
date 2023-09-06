package peaksoft.house.gadgetariumb9.dto.response.compare;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class LatestComparison {

    private Long subProductId;

    private String image;

    private String name;

    private BigDecimal price;


}
