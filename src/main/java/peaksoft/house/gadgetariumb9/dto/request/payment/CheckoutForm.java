package peaksoft.house.gadgetariumb9.dto.request.payment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutForm {
    @NotNull
    @Min(4)
    private Integer amount;

    @NotNull
    @Max(200)
    private String featureRequest;
}
