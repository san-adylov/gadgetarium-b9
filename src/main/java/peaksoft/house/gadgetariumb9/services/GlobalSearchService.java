package peaksoft.house.gadgetariumb9.services;

import java.time.LocalDate;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.AdminMainPagination;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;

public interface GlobalSearchService {
    GlobalSearchResponse globalSearch(String keyword);

    AdminMainPagination adminSearch (String keyword,String productType, LocalDate startDate, LocalDate endDate, int pageSize, int pageNumber);
}
