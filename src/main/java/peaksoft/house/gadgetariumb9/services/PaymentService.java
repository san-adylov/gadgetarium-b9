package peaksoft.house.gadgetariumb9.services;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

public interface PaymentService {

    Customer createCustomer(String token, String email) throws StripeException;

    Charge chargeNewCard(String token, double amount) throws StripeException;

    Charge chargeCustomerCard(String customerId, int amount) throws Exception;

}
