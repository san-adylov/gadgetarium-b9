package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.request.review.AnswerRequest;
import peaksoft.house.gadgetariumb9.dto.request.review.ReviewRequest;
import peaksoft.house.gadgetariumb9.dto.request.review.ReviewUserRequest;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewGradeInfo;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewPagination;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewRatingResponse;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewUserResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface ReviewService {

    ReviewRatingResponse countReviewsRating(Long subProductId);

    SimpleResponse saveReview(ReviewRequest reviewRequest);

    ReviewPagination getAllReviews(Long subProductId,int pageSize, int numberPage);

    SimpleResponse deleteReview(Long reviewId);

    SimpleResponse replyToComment(AnswerRequest answerRequest);

    SimpleResponse updateAnswer(Long reviewId, String text);

    ReviewGradeInfo getFeedback(Long subProductId);

    ReviewUserResponse updateComment(ReviewUserRequest request);

    ReviewUserResponse deleteComment(Long reviewId);

}
