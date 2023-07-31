package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.response.basket.BasketInfographicResponse;
import peaksoft.house.gadgetariumb9.dto.response.basket.BasketResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.BasketService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/basket")
@Tag(name = "Basket API", description = "Endpoints for managing user's shopping basket")
public class BasketApi {
    
    private final BasketService basketService;

    @PostMapping("/{sub-product-id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Save basket", description = "Method to add a sub product to the user's basket")
    public SimpleResponse saveBasket(@PathVariable("sub-product-id") Long subProductId) {
        return basketService.saveBasket(subProductId);
    }

    @GetMapping("/get-infographic")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get infographic", description = "Method of obtaining information about the quantity and price of goods")
    public BasketInfographicResponse getInfographic() {
        return basketService.getInfographic();
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all basket", description = "Method to get information about products in the user's basket")
    public List<BasketResponse> getBasket() {
        return basketService.getAllByProductsFromTheBasket();
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete sub products", description = "Method to delete multiple sub products from the user's basket")
    public SimpleResponse deleteSubProductByIds(@RequestBody List<Long> subProductIds) {
        return basketService.deleteProductByIds(subProductIds);
    }

    @DeleteMapping("/{sub-product-id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete sub product", description = "Method to delete a sub product from the user's basket")
    public SimpleResponse deleteBySubProductId(@PathVariable("sub-product-id") Long subProductId) {
        return basketService.deleteProductById(subProductId);
    }
}