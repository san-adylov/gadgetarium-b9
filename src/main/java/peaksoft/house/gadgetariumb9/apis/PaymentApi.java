package peaksoft.house.gadgetariumb9.apis;

import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePaymentRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.PaymentService;


@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Tag(name = "Payment Stripe API")
public class PaymentApi {

    private final PaymentService paymentService;


    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @PostMapping
    @Operation(summary = "Creating a payment",
            description = "This method creates payment with stripe system.")
    public SimpleResponse chargeCreditCard(@RequestBody CreatePaymentRequest request) throws  StripeException {
        return paymentService.chargeCreditCard(request);
    }

    @PostMapping("/webhook")
    @Operation()
    public String handleWebhookEvent(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader){
        return paymentService.handleWebhookEvent(payload,sigHeader);
    }

    @GetMapping
    public String key() {
        return stripePublicKey;
    }


}
