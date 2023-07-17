package peaksoft.house.gadgetariumb9.dto.request.discount;

import lombok.Builder;

@Builder
public record DiscountRequest(

    int amountOfDiscount,

    String discountStartDate,

    String discountEndDate,

    Long subProductId
) {
}
