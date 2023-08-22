package peaksoft.house.gadgetariumb9.apis;

import com.stripe.model.Charge;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.services.PaymentService;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Tag(name = "Payment Stripe API")
public class PaymentApi {

    private final PaymentService paymentService;

    @PostMapping("/charge")
    public Charge chargeCard(@RequestHeader(value = "token") String token, @RequestHeader(value = "amount") Double amount) throws Exception {
        System.out.println(token);
        System.out.println(amount);
        return this.paymentService.chargeNewCard(token, amount);

    }


}
