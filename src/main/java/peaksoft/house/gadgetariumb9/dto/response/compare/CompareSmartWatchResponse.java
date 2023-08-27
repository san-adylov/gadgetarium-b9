package peaksoft.house.gadgetariumb9.dto.response.compare;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import peaksoft.house.gadgetariumb9.enums.*;
import java.math.BigDecimal;


@SuperBuilder
@NoArgsConstructor
public class CompareSmartWatchResponse extends CompareProductResponse{

    private String anInterface;

    private String hullShape;

    private String materialBracelet;

    private String  housingMaterial;

    private String gender;

    private boolean waterproof;

    private double displayDiscount;

    public CompareSmartWatchResponse(Long subProductId, Long prId, String brandName,
                                     String prodName, BigDecimal price, String color,
                                     String screen, int rom, String operationalSystems,
                                     String catTitle, String subCatTitle, String image,
                                     String anInterface, String hullShape,
                                     String materialBracelet, String housingMaterial,
                                     String gender, boolean waterproof, double displayDiscount) {
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