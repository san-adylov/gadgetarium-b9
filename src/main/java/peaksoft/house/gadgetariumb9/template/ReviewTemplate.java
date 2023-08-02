package peaksoft.house.gadgetariumb9.template;

import java.util.List;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewGradeInfo;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewPagination;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewResponse;

public interface ReviewTemplate {

  ReviewPagination getAll (Long subProductId,int pageSize, int number);

  ReviewGradeInfo getFeedback(Long subProductId);
}
