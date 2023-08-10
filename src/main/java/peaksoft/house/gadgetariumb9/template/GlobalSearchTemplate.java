package peaksoft.house.gadgetariumb9.template;

import java.util.List;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.AdminSearchResponse;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;

public interface GlobalSearchTemplate {

   GlobalSearchResponse globalSearch(String keyword);

   List<AdminSearchResponse> adminSearch (String keyword);

}
