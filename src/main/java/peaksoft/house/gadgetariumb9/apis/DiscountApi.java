package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.discount.DiscountRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.DiscountService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/discounts")
@Tag(name = "Discount API",description = "API for working with discounts")
public class DiscountApi {

    private final DiscountService discountService;

    @PostMapping
    @Operation(summary = "Save discount", description = "Adding a discount for sub products")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveDiscount(@RequestParam DiscountRequest discountRequest) {
        return discountService.saveDiscount(discountRequest);
    }
}
