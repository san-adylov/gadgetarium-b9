package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.FavoriteService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favorite")
@Tag(name = "Favorite API", description = "API for favorite CRUD management")
@PreAuthorize("hasAnyAuthority('USER')")
public class FavoriteApi {

    private final FavoriteService favoriteService;

    @Operation(summary = "Add or delete", description = "The method adds and, if present, removes")
    @PostMapping("/{subProductId}")
    SimpleResponse addOrDeleteFavorite(@PathVariable Long subProductId) {
        return favoriteService.addAndDeleteFavorite(subProductId);
    }

    @Operation(summary = "Add or delete", description = "The method adds and, if present, removes")
    @PostMapping("/saveAll")
    SimpleResponse addOrDeleteFavorites(@RequestBody List<Long> subProductIds) {
        return favoriteService.addSubProductsToFavorite(subProductIds);
    }

    @Operation(summary = "Clear favorite", description = "Method to clear selected available USER")
    @DeleteMapping
    SimpleResponse clearFavorite() {
        return favoriteService.clearFavorite();
    }

    @Operation(summary = "Get all favorite", description = "Method exports favorites available to USER himself")
    @GetMapping
    List<SubProductResponse> getAllFavorite() {
        return favoriteService.getAllFavorite();
    }

}
