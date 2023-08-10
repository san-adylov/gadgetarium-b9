package peaksoft.house.gadgetariumb9.dto.response.compare;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;


@NoArgsConstructor
@SuperBuilder
public class CompareSmartPhoneResponse extends CompareProductResponse{

    private int simCard;

    private String diagonalScreen;

    private String batteryCapacity;

    private double screenSize;

    public CompareSmartPhoneResponse(Long subProductId, Long prId, String brandName, String prodName,
                                     BigDecimal price, String color, String screen, int rom,
                                     String operationalSystems, String catTitle, String subCatTitle,
                                     String image, int simCard, String diagonalScreen,
                                     String batteryCapacity, double screenSize) {
        super(subProductId, prId, brandName, prodName, price, color, screen, rom, operationalSystems, catTitle, subCatTitle, image);
        this.simCard = simCard;
        this.diagonalScreen = diagonalScreen;
        this.batteryCapacity = batteryCapacity;
        this.screenSize = screenSize;
    }
}
