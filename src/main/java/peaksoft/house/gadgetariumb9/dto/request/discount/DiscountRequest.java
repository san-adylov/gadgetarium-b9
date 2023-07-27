package peaksoft.house.gadgetariumb9.dto.request.discount;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRequest {

    private int amountOfDiscount;

    private LocalDate discountStartDate;

    private LocalDate discountEndDate;

    private List<Long> subProductIds;
}
