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

    @Operation(summary = "add or delete", description = "This method may or may not accept")
    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("/add Or Delete Favorite")
    SimpleResponse addOrDeleteFavorite(@RequestBody Long subProductId) {
        return favoriteService.addAndDeleteFavorite(subProductId);
    }

    @Operation(summary = "clear favorite", description = "This method can clear favorites")
    @PreAuthorize("hasAnyAuthority('USER')")
    @DeleteMapping("/clear-favorite")
    SimpleResponse clearFavorite() {
        return favoriteService.clearFavorite();
    }

    @Operation(summary = "get all favorite", description = "This method allows all objects to be seen")
    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/get-all-favorite")
    List<SubProductResponse> getAllFavorite() {
        return favoriteService.getAllFavorite();
    }

    @Operation(summary = "delete Favorite", description = "This method allows you to delete favorites")
    @PreAuthorize("hasAnyAuthority('USER')")
    @DeleteMapping
    SimpleResponse deleteFavorite(@RequestBody List<Long> subProductId) {
        return favoriteService.deleteFavorite(subProductId);
    }
}
