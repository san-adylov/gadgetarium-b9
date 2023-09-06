package peaksoft.house.gadgetariumb9.dto.response.compare;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class CompareSmartWatchResponse extends CompareProductResponse {

    private String anInterface;

    private String hullShape;

    private String materialBracelet;

    private String housingMaterial;

    private String gender;

    private boolean waterproof;

    private double displayDiscount;
}