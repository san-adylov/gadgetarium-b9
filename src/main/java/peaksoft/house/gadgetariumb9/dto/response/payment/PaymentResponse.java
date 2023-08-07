package peaksoft.house.gadgetariumb9.dto.response.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResponse {
    private String payment_intent;
    private String payment_intent_client_secret;
    private String redirect_status;
}
