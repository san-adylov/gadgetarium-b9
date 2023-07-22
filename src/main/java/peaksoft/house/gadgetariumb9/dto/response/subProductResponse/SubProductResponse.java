package peaksoft.house.gadgetariumb9.dto.response.subProductResponse;

import lombok.*;
import java.math.BigDecimal;

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

    private String quantity;

    private String codeColor;

    private String image;

    private Long articleNumber;

    private int discount;

    public SubProductResponse(Long id, int ram, String screenResolution, int rom, String additionalFeatures, BigDecimal price, int quantity, String codeColor,  Long articleNumber,String image, int discount) {
        this.id = id;
        this.ram = ram;
        this.screenResolution = screenResolution;
        this.rom = rom;
        this.additionalFeatures = additionalFeatures;
        this.price = price;
        if (quantity >= 1){
            this.quantity = "В наличии";
        }else {
            this.quantity = "Не в наличии";
        }
        this.codeColor = codeColor;
        this.articleNumber = articleNumber;
        this.image = image;
        this.discount = discount;
    }

}
