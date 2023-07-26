package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductHistoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.services.SubProductHistory;
import peaksoft.house.gadgetariumb9.services.SubProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subProduct")
@Tag(name = "Sub product", description = "API for working with sub product")
public class SubProductApi {

  private final SubProductService subProductService;
  private final SubProductHistory subProductHistory;

  @PostMapping("/filter")
  @Operation(summary = "Filter catalog", description = "Method for filtering products")
  @PermitAll
  public SubProductPagination filter(
      @RequestBody SubProductCatalogRequest subProductCatalogRequest,
      @RequestParam(defaultValue = "6") int pageSize,
      @RequestParam(defaultValue = "1") int pageNumber) {
    return subProductService.getSubProductCatalogs(subProductCatalogRequest, pageSize, pageNumber);
  }

  @PostMapping("recently-viewed/{productId}")
  @Operation(summary = "recently viewed", description = "Method for Adding Browsing History")
  @PreAuthorize("hasAuthority('USER')")
  public void addRecentlyViewedProduct(@PathVariable Long productId) {
    subProductHistory.addRecentlyViewedProduct(productId);
  }

  @GetMapping("/recently-viewed")
  @Operation(summary = "Get products by recently viewed", description = "Browsing history method")
  @PreAuthorize("hasAuthority('USER')")
  public List<SubProductHistoryResponse> getRecentlyViewedProducts() {
    return subProductHistory.getRecentlyViewedProduct();
  }

}
