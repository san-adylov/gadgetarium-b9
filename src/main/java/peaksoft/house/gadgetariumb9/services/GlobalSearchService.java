package peaksoft.house.gadgetariumb9.services;

import java.util.List;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.AdminSearchResponse;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;

public interface GlobalSearchService {
    GlobalSearchResponse globalSearch(String keyword);

    List<AdminSearchResponse> adminSearch (String keyword);
}
