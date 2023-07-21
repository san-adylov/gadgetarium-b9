package peaksoft.house.gadgetariumb9.service;

import peaksoft.house.gadgetariumb9.dto.request.review.ReviewRatingRequest;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewRatingResponse;

public interface ReviewService {

    ReviewRatingResponse countReviewsRating(ReviewRatingRequest request);

}
