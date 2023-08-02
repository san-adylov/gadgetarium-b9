package peaksoft.house.gadgetariumb9.dto.request.payment;

import com.paypal.orders.Payer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest     {
    private String intent;
    private List<PurchaseUnitRequest> purchaseUnits;
    private String returnUrl;
    private String cancelUrl;
    private Payer payer;
}
