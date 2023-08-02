package peaksoft.house.gadgetariumb9.apis;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.payment.PaymentRequest;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class PayPalController {

    private final PayPalHttpClient payPalHttpClient;

    @PostMapping("/createPayment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        request.requestBody(buildRequestBody(paymentRequest));

        try {
            HttpResponse<Order> response = payPalHttpClient.execute(request);
            Order order = response.result();
            return ResponseEntity.ok(order);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment.");
        }
    }

    private OrderRequest buildRequestBody(PaymentRequest paymentRequest) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent(paymentRequest.getIntent());
        orderRequest.purchaseUnits();
        orderRequest.applicationContext(new ApplicationContext().returnUrl(paymentRequest.getReturnUrl())
                .cancelUrl(paymentRequest.getCancelUrl()));
        orderRequest.payer(paymentRequest.getPayer());
        return orderRequest;
    }
}
