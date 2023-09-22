package peaksoft.house.gadgetariumb9.template;

import peaksoft.house.gadgetariumb9.dto.response.review.AdminReviewPagination;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewGradeInfo;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewPagination;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewsRatings;

public interface ReviewTemplate {

  ReviewPagination getAll (Long subProductId,int pageSize, int number);

  ReviewGradeInfo getFeedback(Long subProductId);

  AdminReviewPagination getAllReviewsForAdmin(String filter, int pageSize, int pageNumber);

  ReviewsRatings getAllRatings ();
}
