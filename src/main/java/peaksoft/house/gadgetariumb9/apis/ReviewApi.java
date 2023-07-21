package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewRatingResponse;
import peaksoft.house.gadgetariumb9.services.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Tag(name = "Review API", description = "Endpoints for managing and retrieving reviews for products.")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReviewApi {

    private final ReviewService service;

    @PermitAll
    @Operation(summary = "Rating of reviews", description = "Get the rating summary of reviews for a specific product..")
    @GetMapping()
    public ReviewRatingResponse countReviewsRating(@Valid @RequestParam  Long subProductId) {
        return service.countReviewsRating(subProductId);
    }
}
