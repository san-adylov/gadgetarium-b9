package peaksoft.house.gadgetariumb9.dto.response.review;

import java.util.List;
import lombok.*;

@Getter
@NoArgsConstructor
@Builder
public class ReviewPagination {

  private List<ReviewResponse> responseList;

  private int pageSize;

  private int numberPage;


  public ReviewPagination(List<ReviewResponse> responseList, int pageSize,
      int numberPage) {
    this.responseList = responseList;
    this.pageSize = pageSize;
    this.numberPage = numberPage;
  }

}
