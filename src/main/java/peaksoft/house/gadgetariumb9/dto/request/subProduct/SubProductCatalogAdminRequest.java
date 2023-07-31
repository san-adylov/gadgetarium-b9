package peaksoft.house.gadgetariumb9.dto.request.subProduct;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SubProductCatalogAdminRequest {

    private LocalDate startDate;

    private LocalDate endDate;

    private int ram;

    private String screenResolution;

    private int rom;

    private String additionalFeatures;

    private int sale;

    private int quantity;

    private String codeColor;

    private double rating;

    private List<String> images;

}
