package peaksoft.house.gadgetariumb9.services.serviceImpl;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePaymentRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.PaymentService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PaymentServiceImpl implements PaymentService {

    @Override
    public SimpleResponse chargeNewCard(CreatePaymentRequest createPaymentRequest) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (createPaymentRequest.getAmount() * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", createPaymentRequest.getToken());
        Charge charge = Charge.create(chargeParams);
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .message(charge.getCustomer())
                .build();
    }

}
