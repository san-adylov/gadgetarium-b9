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

}
