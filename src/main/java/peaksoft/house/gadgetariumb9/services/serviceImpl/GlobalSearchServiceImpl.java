package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;
import peaksoft.house.gadgetariumb9.services.GlobalSearchService;
import peaksoft.house.gadgetariumb9.template.GlobalSearchTemplate;

@RequiredArgsConstructor
@Transactional
@Service
public class GlobalSearchServiceImpl implements GlobalSearchService {

    private final GlobalSearchTemplate globalSearchTemplate;

    @Override
    public GlobalSearchResponse globalSearch(String keyword) {
        return globalSearchTemplate.globalSearch(keyword);
    }
}
