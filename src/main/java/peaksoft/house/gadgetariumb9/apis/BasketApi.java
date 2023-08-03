package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.response.basket.BasketInfographicResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.BasketService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/basket")
@Tag(name = "Basket API", description = "Endpoints for managing user's shopping basket")
public class BasketApi {

    private final BasketService basketService;

    @PostMapping("/{subProductId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add Sub product to Basket", description = "Add a sub product to the user's shopping basket")
    public SimpleResponse addSubProductToBasket(@PathVariable Long subProductId) {
        return basketService.saveBasket(subProductId);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retrieve User's Basket", description = "Get information about the products in the user's shopping basket")
    public BasketInfographicResponse getUserBasket() {
        return basketService.getAllByProductsFromTheBasket();
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete sub products", description = "Method to delete multiple sub products from the user's basket")
    public SimpleResponse deleteSubProductByIds(@RequestBody List<Long> subProductIds) {
        return basketService.deleteProductByIds(subProductIds);
    }

    @DeleteMapping("/{subProductId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete sub product", description = "Method to delete a sub product from the user's basket")
    public SimpleResponse deleteBySubProductId(@PathVariable Long subProductId) {
        return basketService.deleteProductById(subProductId);
    }
}