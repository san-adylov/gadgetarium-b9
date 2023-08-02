package peaksoft.house.gadgetariumb9.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String name;
    private String description;
    private String sku;
    private String unitAmount;
    private String currencyCode;
    private int quantity;
}
