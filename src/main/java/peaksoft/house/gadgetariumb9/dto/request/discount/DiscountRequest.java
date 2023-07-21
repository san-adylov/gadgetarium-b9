package peaksoft.house.gadgetariumb9.dto.request.discount;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
