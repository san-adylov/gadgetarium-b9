package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;

public interface GlobalSearchService {
    GlobalSearchResponse globalSearch(String keyword);
}
