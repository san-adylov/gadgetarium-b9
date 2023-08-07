package peaksoft.house.gadgetariumb9.apis.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePayment;
import peaksoft.house.gadgetariumb9.dto.response.payment.CreatePaymentResponse;

@RestController
public class PaymentController {

    @PostMapping("/create-payment-intent")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Operation(summary = "Create Payment Intent", description = "This endpoint creates a payment intent for a given amount in KGS currency.")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment request) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(request.getAmount() * 100L)
                        .setCurrency("kgs")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build())
                        .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return new CreatePaymentResponse(paymentIntent.getClientSecret());
    }

}
