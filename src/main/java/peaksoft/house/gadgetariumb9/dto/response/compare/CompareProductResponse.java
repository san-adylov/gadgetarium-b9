package peaksoft.house.gadgetariumb9.dto.response.compare;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@SuperBuilder
@Getter
@NoArgsConstructor
@Setter
public class CompareProductResponse {

    private Long subProductId;

    private Long prId;

    private String brandName;

    private String prodName;

    private BigDecimal price;

    private String color;

    private String screen;

    private int rom;

    private String catTitle;

    private String subCatTitle;

    private String image;

    private boolean in_basket;

    public CompareProductResponse(Long subProductId, Long prId, String brandName, String prodName,
                                  BigDecimal price, String color, String screen, int rom,
                                  String catTitle,
                                  String subCatTitle, String image) {
        this.subProductId= subProductId;
        this.prId = prId;
        this.brandName = brandName;
        this.prodName = prodName;
        this.price = price;
        this.color = color;
        this.screen = screen;
        this.rom = rom;
        this.catTitle = catTitle;
        this.subCatTitle = subCatTitle;
        this.image = image;
    }
}
