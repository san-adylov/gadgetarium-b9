package peaksoft.house.gadgetariumb9.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseUnitRequest {
    private AmountWithBreakdown amount;
    private List<Item> items;
}
