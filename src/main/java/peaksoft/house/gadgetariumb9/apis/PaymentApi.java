package peaksoft.house.gadgetariumb9.apis;

import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.payment.CreatePaymentRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.PaymentService;
import peaksoft.house.gadgetariumb9.services.serviceImpl.PdfFileServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Tag(name = "Payment Stripe API")
public class PaymentApi {

    private final PaymentService paymentService;
    private final PdfFileServiceImpl pdfFileService;

    @PostMapping
    public SimpleResponse chargeCard(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) throws StripeException {
        return paymentService.chargeNewCard(createPaymentRequest);
    }
}
