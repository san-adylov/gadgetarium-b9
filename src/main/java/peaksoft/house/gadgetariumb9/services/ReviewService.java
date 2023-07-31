package peaksoft.house.gadgetariumb9.services;

import java.util.List;
import peaksoft.house.gadgetariumb9.dto.request.review.AnswerRequest;
import peaksoft.house.gadgetariumb9.dto.request.review.ReviewRequest;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewRatingResponse;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface ReviewService {

    ReviewRatingResponse countReviewsRating(Long subProductId);

    SimpleResponse saveReview(ReviewRequest reviewRequest);

    List<ReviewResponse> getAllReviews(Long subProductId);

    SimpleResponse deleteReview(Long reviewId);

    SimpleResponse replyToComment(AnswerRequest answerRequest);

    SimpleResponse updateAnswer(AnswerRequest answerRequest);

}
