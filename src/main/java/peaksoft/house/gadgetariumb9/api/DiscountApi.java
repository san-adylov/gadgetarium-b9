package peaksoft.house.gadgetariumb9.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/discount")
@CrossOrigin(maxAge = 3600,origins = "*")
@Tag(name = "Discount",description = "Discount api")
public class DiscountApi {

  private final DiscountService discountService;

  @PostMapping("/save-discount")
  @Operation(summary = "Save discount",description = "Save discount")
  @PreAuthorize("hasAuthority('ADMIN')")
  public SimpleResponse saveDiscount(@RequestParam DiscountRequest discountRequest){
    return discountService.saveDiscount(discountRequest);
  }
}
