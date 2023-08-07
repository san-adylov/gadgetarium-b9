package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.response.payment.PaymentResponse;

public interface PaymentService {
     PaymentResponse paymentMethod(String token);
}
