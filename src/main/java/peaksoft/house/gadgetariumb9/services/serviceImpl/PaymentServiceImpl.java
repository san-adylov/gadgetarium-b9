package peaksoft.house.gadgetariumb9.services.serviceImpl;

import com.google.gson.JsonSyntaxException;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePaymentRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.PaymentService;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.webhook}")
    private String ENDPOINT_SECRET;

    @Override
    public SimpleResponse chargeCreditCard(CreatePaymentRequest paymentRequest)  {
        try {
            Map<String, Object> chargeParams = new HashMap<>();
            int amount = (int) Math.round(paymentRequest.getAmount() * 100);
            chargeParams.put("amount", amount);
            chargeParams.put("currency", paymentRequest.getCurrency());
            chargeParams.put("source", paymentRequest.getToken());
            Charge.create(chargeParams);
            log.info("successfully the charge credit card method works ");
            return new SimpleResponse("Payment successful!", HttpStatus.OK);
        } catch (StripeException e) {
            log.error("Something wrong with token");
            return new SimpleResponse(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public String handleWebhookEvent(String payload,String sigHeader) {
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, ENDPOINT_SECRET);
        } catch (JsonSyntaxException | SignatureVerificationException e) {
            return "Bad Request";
        }
        switch (event.getType()) {
            case "charge.failed" -> {
                return "Payment Failed " + HttpStatus.BAD_REQUEST.name();
            }
            case "charge.refunded" -> {
                return "Payment Refunded " + HttpStatus.OK.name();
            }
            case "charge.succeeded" -> {
                return "Payment Succeeded " + HttpStatus.OK.name();
            }
            default -> {
                return "Unhandled event type: " + event.getType();
            }
        }
    }

}
