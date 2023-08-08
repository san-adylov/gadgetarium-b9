package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;
import peaksoft.house.gadgetariumb9.services.GlobalSearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/globalSearch")
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
@Tag(name = "Global search API", description = "API method global search")
public class GlobalSearchApi {

    private final GlobalSearchService globalSearchService;

    @GetMapping
    @Operation(summary = "Search", description = "This method gets all products by searching.")
    GlobalSearchResponse globalSearch(@RequestParam(value = "keyword", required = false) String keyword) {
        return globalSearchService.globalSearch(keyword);
    }
}
