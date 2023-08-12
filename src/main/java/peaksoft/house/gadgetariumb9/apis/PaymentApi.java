package peaksoft.house.gadgetariumb9.apis;

import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePaymentRequest;
import peaksoft.house.gadgetariumb9.dto.response.payment.CreatePaymentResponse;
import peaksoft.house.gadgetariumb9.services.PaymentService;

@RestController
@RequestMapping("/api/user/stripe")
@RequiredArgsConstructor
@Tag(name = "Payment Stripe API")
public class PaymentApi {

    private final PaymentService paymentService;


    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @PostMapping("/create-payment-intent")
    @Operation(summary = "Creating a payment",
            description = "This method creates payment with stripe system.")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePaymentRequest request) throws  StripeException {
        return paymentService.createPaymentIntent(request);
    }

    @GetMapping("/public_key")
    public String key() {
        return stripePublicKey;
    }

}
