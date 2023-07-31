package peaksoft.house.gadgetariumb9.dto.response.subProduct;

import lombok.*;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubProductResponse {

    private String name;

    private String brandName;

    private Long subProductId;

    private int ram;

    private String screenResolution;

    private int rom;

    private String additionalFeatures;

    private BigDecimal price;

    private String quantity;

    private String codeColor;

    private String image;

    private Long articleNumber;

    private int discount;

    public SubProductResponse(String brandName, String name, Long subProductId, int ram, String screenResolution, int rom, String additionalFeatures, BigDecimal price, int quantity, String codeColor, Long articleNumber, String image, int discount) {
        this.brandName = brandName;
        this.name = name;
        this.subProductId = subProductId;
        this.ram = ram;
        this.screenResolution = screenResolution;
        this.rom = rom;
        this.additionalFeatures = additionalFeatures;
        this.price = price;
        if (quantity >= 1) {
            this.quantity = "В наличии";
        } else {
            this.quantity = "Не в наличии";
        }
        this.codeColor = codeColor;
        this.articleNumber = articleNumber;
        this.image = image;
        this.discount = discount;
    }

}
