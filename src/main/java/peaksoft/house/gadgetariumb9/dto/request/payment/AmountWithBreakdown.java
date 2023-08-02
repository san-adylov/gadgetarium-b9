package peaksoft.house.gadgetariumb9.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AmountWithBreakdown {
    private String currencyCode;
    private String value;
}
