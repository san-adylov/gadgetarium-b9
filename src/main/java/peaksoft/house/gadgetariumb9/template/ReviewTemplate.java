package peaksoft.house.gadgetariumb9.template;

import java.util.List;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewGradeInfo;
import peaksoft.house.gadgetariumb9.dto.response.review.ReviewResponse;

public interface ReviewTemplate {

  List<ReviewResponse> getAll (Long subProductId);

  ReviewGradeInfo getFeedback(Long subProductId);
}
