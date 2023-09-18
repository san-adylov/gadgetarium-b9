package peaksoft.house.gadgetariumb9.template;

import java.time.LocalDate;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.AdminMainPagination;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;

public interface GlobalSearchTemplate {

   GlobalSearchResponse globalSearch(String keyword);

   AdminMainPagination adminSearch(String keyword, String productType,String sortType, String filterType, LocalDate startDate, LocalDate endDate,int pageSize, int pageNumber);

}
