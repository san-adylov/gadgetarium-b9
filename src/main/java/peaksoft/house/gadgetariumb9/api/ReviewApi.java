package peaksoft.house.gadgetariumb9.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.review.ReviewRatingRequest;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewRatingResponse;
import peaksoft.house.gadgetariumb9.service.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@PermitAll
@Tag(name = "Review API", description = "Endpoints for managing and retrieving reviews for products.")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReviewApi {

    private final ReviewService service;

    @Operation(summary = "Rating of reviews", description = "Get the rating summary of reviews for a specific product.")
    @GetMapping()
    public ReviewRatingResponse countReviewsRating(@Valid ReviewRatingRequest request) {
        return service.countReviewsRating(request);
    }
}
