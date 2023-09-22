package peaksoft.house.gadgetariumb9.dto.response.review;

import java.util.List;
import lombok.*;

@Getter
@NoArgsConstructor
@Builder
public class AdminReviewPagination {

  private List<AdminAllReviews> reviews;

  private int pageSize;

  private int numberPage;

  private int count;

  public AdminReviewPagination(List<AdminAllReviews> reviews, int pageSize,
      int numberPage, int count) {
    this.reviews = reviews;
    this.pageSize = pageSize;
    this.numberPage = numberPage;
    this.count = count;
  }
}