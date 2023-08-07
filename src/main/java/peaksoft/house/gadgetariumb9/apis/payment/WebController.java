package peaksoft.house.gadgetariumb9.apis.payment;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import peaksoft.house.gadgetariumb9.dto.request.payment.CheckoutForm;
import peaksoft.house.gadgetariumb9.services.PaymentService;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final PaymentService paymentServices;

    @GetMapping("/")
    public String home(@NotNull Model model) {
        model.addAttribute("checkoutForm", new CheckoutForm());
        return "index1";
    }

    @PostMapping("/")
    public String checkout(@ModelAttribute("checkoutForm") CheckoutForm checkoutForm, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index1";
        }
        model.addAttribute("amount", checkoutForm.getAmount());
        return "checkout";
    }

    @GetMapping("/payment/{paymentId}")
    public String payment(@PathVariable("paymentId") String paymentId, Model model) {
        model.addAttribute("response", paymentServices.paymentMethod(paymentId));
        return "payment";
    }
}
