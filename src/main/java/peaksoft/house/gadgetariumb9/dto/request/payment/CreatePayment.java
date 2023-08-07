package peaksoft.house.gadgetariumb9.dto.request.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreatePayment {

    private Integer amount;

    private String featureResponse;
}
