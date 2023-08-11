package peaksoft.house.gadgetariumb9.dto.response.compare;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import peaksoft.house.gadgetariumb9.enums.*;
import java.math.BigDecimal;


@SuperBuilder
@Getter
public class CompareSmartWatchResponse extends CompareProductResponse{

    private Interface anInterface;

    private HullShape hullShape;

    private MaterialBracelet materialBracelet;

    private HousingMaterial housingMaterial;

    private Gender gender;

    private boolean waterproof;

    private double displayDiscount;

    public CompareSmartWatchResponse(Long subProductId, Long prId, String brandName,
                                     String prodName, BigDecimal price, String color,
                                     String screen, int rom, String operationalSystems,
                                     String catTitle, String subCatTitle, String image,
                                     Interface anInterface, HullShape hullShape,
                                     MaterialBracelet materialBracelet, HousingMaterial housingMaterial,
                                     Gender gender, boolean waterproof, double displayDiscount) {
        super(subProductId, prId, brandName, prodName, price, color, screen, rom, operationalSystems, catTitle, subCatTitle, image);
        this.anInterface = anInterface;
        this.hullShape = hullShape;
        this.materialBracelet = materialBracelet;
        this.housingMaterial = housingMaterial;
        this.gender = gender;
        this.waterproof = waterproof;
        this.displayDiscount = displayDiscount;
    }
}