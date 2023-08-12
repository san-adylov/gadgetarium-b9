package peaksoft.house.gadgetariumb9.services;

import com.stripe.exception.StripeException;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePaymentRequest;
import peaksoft.house.gadgetariumb9.dto.response.payment.CreatePaymentResponse;

public interface PaymentService {
     CreatePaymentResponse createPaymentIntent(CreatePaymentRequest request) throws StripeException;
}
