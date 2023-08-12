package peaksoft.house.gadgetariumb9.dto.request.payment;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreatePaymentRequest{
    @Min(1)
    private Integer amount;
}
