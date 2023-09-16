package peaksoft.house.gadgetariumb9.dto.response.globalSearch;

import java.util.List;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminMainPagination {

  private List<AdminSearchResponse> adminSearchResponses;

  private int pageSize;

  private int pageNumber;

  private int countOfProducts;
}
