package peaksoft.house.gadgetariumb9.apis;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentApi {

    @Value("${stripe.apikey}")
    private String stripeKey;

    public String index() throws StripeException {
        Map<String,Object> params = new HashMap<>();
        Stripe.apiKey = stripeKey;
        params.put("name",null);
        params.put("email",null);
        params.put("id",null);
        Customer customer = Customer.create(params);
        return "Ok";
    }
}
