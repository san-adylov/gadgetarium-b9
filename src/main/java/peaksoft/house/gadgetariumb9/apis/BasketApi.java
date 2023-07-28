package peaksoft.house.gadgetariumb9.apis;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.response.basket.BasketResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.BasketService;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
public class BasketApi {
    private final BasketService basketService;

    @PostMapping("/{subProductId}")
    @PreAuthorize("permitAll()")
    public SimpleResponse saveBasket(@PathVariable Long subProductId) {
        return basketService.saveBasket(subProductId);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<BasketResponse> getBasket() {
        return basketService.getAllByProductsFromTheBasket();
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public SimpleResponse delete(@RequestBody List<Long> subProductIds) {
        return basketService.deleteProductByIds(subProductIds);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public SimpleResponse delete(@PathVariable Long id) {
        return basketService.deleteProductById(id);
    }

}
