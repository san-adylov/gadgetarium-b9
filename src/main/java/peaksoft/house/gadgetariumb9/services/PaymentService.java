package peaksoft.house.gadgetariumb9.services;

import com.stripe.exception.StripeException;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePaymentRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface PaymentService {

    SimpleResponse chargeNewCard(CreatePaymentRequest createPaymentRequest) throws StripeException;

}
