package peaksoft.house.gadgetariumb9.dto.response.compare;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class LatestComparison {

    private String image;

    private String name;

    private BigDecimal price;

    public LatestComparison(String image, String name, BigDecimal price) {
        this.image = image;
        this.name = name;
        this.price = price;
    }
}
