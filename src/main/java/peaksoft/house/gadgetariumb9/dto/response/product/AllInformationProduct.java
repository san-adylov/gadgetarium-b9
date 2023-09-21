package peaksoft.house.gadgetariumb9.dto.response.product;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Builder
public class AllInformationProduct {

    private Long productId;

    private Long supProductId;

    private String productName;

    private List<String> images;

    private Date productDataOfIssue;

    private int productGuarantee;

    private String productDescription;

    private String productPdf;

    private String productVideoLink;

    private int ram;

    private int rom;

    private String screenResolution;

    private String additionalFeatures;

    private BigDecimal price;

    private int quantity;

    private String codeColor;

    private String processor;

    private String purpose;

    private double laptopScreenSize;

    private int videoMemory;

    private int sim;

    private String diagonalScreen;

    private String batteryCapacity;

    private double phoneScreenSize;

    private String anInterface;

    private String hullShape;

    private String materialBracelet;

    private String housingMaterial;

    private String gender;

    private boolean waterproof;

    private double displayDiscount;

    private String categoryTitle;

    private String subCategoryTitle;

    private String brandTitle;

}
