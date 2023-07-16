package peaksoft.house.gadgetariumb9.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.discount.DiscountRequest;
import peaksoft.house.gadgetariumb9.service.DiscountService;

@RestController
@Tag(name = "Discount")
@RequiredArgsConstructor
@RequestMapping("/discount")
public class DiscountApi {

  private final DiscountService discountService;

  @PostMapping("/save")
  @Operation(summary = "Save discount")
  public SimpleResponse saveDiscount(@RequestParam DiscountRequest discountRequest){
    return discountService.saveDiscount(discountRequest);
  }
}
