package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.review.AnswerRequest;
import peaksoft.house.gadgetariumb9.dto.request.review.ReviewRequest;
import peaksoft.house.gadgetariumb9.dto.request.review.ReviewUserRequest;
import peaksoft.house.gadgetariumb9.dto.request.review.ViewReviewRequest;
import peaksoft.house.gadgetariumb9.dto.response.review.AdminReviewPagination;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewGradeInfo;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewPagination;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewRatingResponse;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewUserResponse;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewsRatings;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Tag(name = "Review API", description = "Endpoints for managing and retrieving reviews for products.")
public class ReviewApi {

    private final ReviewService service;

    @Operation(summary = "Rating of reviews", description = "Get the rating summary of reviews for a specific product..")
    @GetMapping("/rating")
    public ReviewRatingResponse countReviewsRating(@RequestParam Long subProductId) {
        return service.countReviewsRating(subProductId);
    }

    @PermitAll
    @GetMapping("/get-all")
    @Operation(summary = "All reviews", description = "Get all reviews by subProduct id")
    public ReviewPagination getAllReview(@RequestParam Long subProductId,
                                         @RequestParam(defaultValue = "5") int pageSize,
                                         @RequestParam(defaultValue = "1") int numberPage) {
        return service.getAllReviews(subProductId, pageSize, numberPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-all-reviews")
    @Operation(summary = "Get all reviews", description = "Get all reviews for product")
    public AdminReviewPagination getAllReviewsForAdmin(@RequestParam(defaultValue = "Все отзывы") String filter,
                                                       @RequestParam(defaultValue = "5") int pageSize,
                                                       @RequestParam(defaultValue = "1") int numberPage) {
        return service.getAllReviewsForAdmin(filter, pageSize, numberPage);
    }

    @PermitAll
    @GetMapping("/all-ratings-info")
    @Operation(summary = "Get all ratings", description = "Get all ratings for products")
    public ReviewsRatings getAllRatings (){
        return service.getAllRatings();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Operation(summary = "Save review", description = "Leave a review if the user bought this product")
    public SimpleResponse saveReview(@RequestBody ReviewRequest reviewRequest) {
        return service.saveReview(reviewRequest);
    }

    @DeleteMapping("/{review_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete review", description = "Delete review by id")
    public SimpleResponse deleteReview(@PathVariable Long review_id) {
        return service.deleteReview(review_id);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update answer", description = "Update the answer to the question that the ADMIN left")
    public SimpleResponse updateAnswer(@RequestBody AnswerRequest answerRequest) {
        return service.updateAnswer(answerRequest.getReviewId(), answerRequest.getReplyToComment());
    }

    @PostMapping("/reply")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Reply to comment", description = "Admin answer to a comment left on this product")
    public SimpleResponse replyToComment(@RequestBody AnswerRequest answerRequest) {
        return service.replyToComment(answerRequest.getReviewId(), answerRequest.getReplyToComment());
    }

    @PermitAll
    @GetMapping
    @Operation(summary = "Get feedback", description = "Output of general statistics of reviews")
    public ReviewGradeInfo getFeedback(@RequestParam Long id) {
        return service.getFeedback(id);
    }

    @PutMapping("/update-comment")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Update user review", description = "Edit your review if not answered yet")
    public ReviewUserResponse updateComment(@RequestBody ReviewUserRequest request) {
        return service.updateComment(request);
    }

    @DeleteMapping("/delete-comment/{reviewId}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Delete user review", description = "Delete your review if no answer")
    public ReviewUserResponse deleteComment(@PathVariable Long reviewId) {
        return service.deleteComment(reviewId);
    }

    @PutMapping("/update-view")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update review viewed", description = "Updating  viewed review for product")
    public SimpleResponse updateReviewView (@RequestBody ViewReviewRequest request){
        return service.updateView(request);
    }
}
