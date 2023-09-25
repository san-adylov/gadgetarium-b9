package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.AdminMainPagination;
import peaksoft.house.gadgetariumb9.dto.response.globalSearch.GlobalSearchResponse;
import peaksoft.house.gadgetariumb9.services.GlobalSearchService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/globalSearch")
@Tag(name = "Global search API", description = "API method global search")
public class GlobalSearchApi {

    private final GlobalSearchService globalSearchService;

    @GetMapping
    @PermitAll
    @Operation(summary = "Search", description = "This method gets all products by searching.")
    public GlobalSearchResponse globalSearch(@RequestParam(value = "keyword", required = false) String keyword) {
        return globalSearchService.globalSearch(keyword);
    }

    @GetMapping("/admin-search")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Admin search", description = "Search for an admin by article, etc.")
    public AdminMainPagination adminSearch(@RequestParam(value = "keyword",defaultValue = "", required = false) String keyword,
                                           @RequestParam(defaultValue = "Все товары", required = false) String productType,
                                           @RequestParam(name = "sort", required = false) String sortType,
                                           @RequestParam(name = "filter", required = false) String filterType,
                                           @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                           @RequestParam(name = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                           @RequestParam(defaultValue = "6") int pageSize,
                                           @RequestParam(defaultValue = "1") int pageNumber) {
        return globalSearchService.adminSearch(keyword,productType, sortType, filterType, startDate,endDate,pageSize,pageNumber);
    }
}
