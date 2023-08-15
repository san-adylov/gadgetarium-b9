package peaksoft.house.gadgetariumb9.services;

import com.stripe.exception.StripeException;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePaymentRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface PaymentService {

     SimpleResponse chargeCreditCard(CreatePaymentRequest request) throws StripeException;

     String handleWebhookEvent(String payload,String sigHeader);
}
