package peaksoft.house.gadgetariumb9.dto.request.subProduct;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.house.gadgetariumb9.enums.Gender;
import peaksoft.house.gadgetariumb9.enums.HousingMaterial;
import peaksoft.house.gadgetariumb9.enums.HullShape;
import peaksoft.house.gadgetariumb9.enums.Interface;
import peaksoft.house.gadgetariumb9.enums.MaterialBracelet;
import peaksoft.house.gadgetariumb9.enums.Processor;
import peaksoft.house.gadgetariumb9.enums.Purpose;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubProductRequest {

    private String codeColor;

    private int rom;

    private int ram;

    private String screenResolution;

    private String additionalFeatures;

    private int quantity;

    private List<String>images;

    private BigDecimal price;

    private Processor processor;

    private Purpose purpose;

    private int videoMemory;

    private double screenSize;

    private int sim;

    private String diagonalScreen;

    private String batteryCapacity;

    private Interface anInterface;

    private HullShape hullShape;

    private MaterialBracelet materialBracelet;

    private HousingMaterial housingMaterial;

    private Gender gender;

    private boolean waterproof;

    private double displayDiscount;
}
