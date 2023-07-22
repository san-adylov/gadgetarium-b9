package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.response.subProductResponse.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.FavoriteService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/favorite")
@Tag(name = "Favorite API", description = "API for favorite CRUD management")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FavoriteApi {

    private final FavoriteService favoriteService;

    @Operation(summary = "Ddd or delete", description = "Honey adds to favorites, if there is, then deletes")
    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/add-0r-delete-favorite")
    SimpleResponse addOrDeleteFavorite(@RequestBody Long subProductId) {
        return favoriteService.addAndDeleteFavorite(subProductId);
    }

    @Operation(summary = "Clear favorite", description = "Method to clear selected available USER")
    @PreAuthorize("hasAnyAuthority('USER')")
    @DeleteMapping("/clear-favorite")
    SimpleResponse clearFavorite() {
        return favoriteService.clearFavorite();
    }

    @Operation(summary = "Get all favorite", description = "Method exports favorites available to USER himself")
    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/get-all-favorite")
    List<SubProductResponse> getAllFavorite() {
        return favoriteService.getAllFavorite();
    }

    @Operation(summary = "Delete Favorite", description = " Method removes products to favorites")
    @PreAuthorize("hasAnyAuthority('USER')")
    @DeleteMapping("/delete-favorite")
    SimpleResponse deleteFavorite(@RequestParam Long subProductId) {
        return favoriteService.deleteFavorite(subProductId);
    }
}
