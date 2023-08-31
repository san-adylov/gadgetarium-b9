package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.AdminSearchResponse;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;
import peaksoft.house.gadgetariumb9.services.GlobalSearchService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/globalSearch")
@Tag(name = "Global search API", description = "API method global search")
public class GlobalSearchApi {

    private final GlobalSearchService globalSearchService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @Operation(summary = "Search", description = "This method gets all products by searching.")
    public GlobalSearchResponse globalSearch(@RequestParam(value = "keyword", required = false) String keyword) {
        return globalSearchService.globalSearch(keyword);
    }

    @GetMapping("/admin-search")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Admin search", description = "Search for an admin by article, etc.")
    public List<AdminSearchResponse> adminSearch(@RequestParam(value = "keyword", required = false) String keyword) {
        return globalSearchService.adminSearch(keyword);
    }
}
