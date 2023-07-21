package peaksoft.house.gadgetariumb9.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.response.brand.subProductResponse.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.service.FavoriteService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/favorite")
@Tag(name = "Favorite", description = "Favorite")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FavoriteApi {

    private final FavoriteService favoriteService;

    @Operation(summary = "add or delete", description = "This method may or may not accept")
    @PreAuthorize("hasAnyAuthority('ADMIN,USER')")
    @PostMapping("/add Or Delete Favorite/{userId}/{subProductId}")
    SimpleResponse addOrDeleteFavorite(@PathVariable Long userId, @PathVariable Long subProductId) {
        return favoriteService.addAndDeleteFavorite(userId, subProductId);
    }

    @Operation(summary = "clear favorite", description = "This method can clear favorites")
    @PreAuthorize("hasAnyAuthority('ADMIN,USER')")
    @DeleteMapping("/clear-favorite/{userId}")
    SimpleResponse clearFavorite(@PathVariable Long userId) {
        return favoriteService.clearFavorite(userId);
    }

    @Operation(summary = "get all favorite", description = "This method allows all objects to be seen")
    @PreAuthorize("hasAnyAuthority('ADMIN,USER')")
    @GetMapping("/get-all-favorite/{userId}")
    List<SubProductResponse> getAllFavorite(@PathVariable Long userId) {
        return favoriteService.getAllFavorite(userId);
    }
@Operation(summary = "delete Favorite",description = "This method allows you to delete favorites")
    SimpleResponse deleteFavorite(@PathVariable List<Long> userId){
        return favoriteService.deleteFavorite(userId);
    }
}
