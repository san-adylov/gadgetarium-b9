package peaksoft.house.gadgetariumb9.dto.response.order;

import java.util.List;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchPagination {

  private List<AdminOrderSearch> adminOrderSearches;

  private int pageSize;

  private int pageNumber;

  private int countOfProducts;
}
