package peaksoft.house.gadgetariumb9.services.serviceImpl;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.payment.PaymentResponse;
import peaksoft.house.gadgetariumb9.services.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${stripe.apikey}")
    private static String STRIPE_API_KEY;


    public static void setup() {
        Stripe.apiKey = STRIPE_API_KEY;
    }

    @Override
    public PaymentResponse paymentMethod(String token) {
        String[] payment = token.split("&");

        String[] split1 = payment[0].split("=");
        String payment_intent = split1[1];

        String[] split2 = payment[1].split("=");
        String payment_intent_client_secret = split2[1];

        String[] split3 = payment[2].split("=");
        String redirect_status = split3[1];
        return new PaymentResponse(payment_intent, payment_intent_client_secret, redirect_status);
    }

}
