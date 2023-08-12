package peaksoft.house.gadgetariumb9.services.serviceImpl;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePaymentRequest;
import peaksoft.house.gadgetariumb9.dto.response.payment.CreatePaymentResponse;
import peaksoft.house.gadgetariumb9.services.PaymentService;

import java.util.Arrays;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public CreatePaymentResponse createPaymentIntent(CreatePaymentRequest request) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(request.getAmount() * 100L)
                        .setCurrency("KGS")
                        .setPaymentMethodTypes(Arrays.asList("card")) // Указываем тип платежного метода
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
