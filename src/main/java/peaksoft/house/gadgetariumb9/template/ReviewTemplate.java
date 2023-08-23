package peaksoft.house.gadgetariumb9.template;

import peaksoft.house.gadgetariumb9.dto.response.review.ReviewGradeInfo;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewPagination;

public interface ReviewTemplate {

  ReviewPagination getAll (Long subProductId,int pageSize, int number);

  ReviewGradeInfo getFeedback(Long subProductId);
}
