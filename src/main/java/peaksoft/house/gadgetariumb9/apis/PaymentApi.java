package peaksoft.house.gadgetariumb9.apis;

import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePaymentRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.PaymentService;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Tag(name = "Payment Stripe API")
public class PaymentApi {

    private final PaymentService paymentService;

    @PostMapping
    public SimpleResponse chargeCard(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) throws StripeException {
        return paymentService.chargeNewCard(createPaymentRequest);
    }


}
