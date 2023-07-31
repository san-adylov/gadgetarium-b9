package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.product.ProductRequest;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.InfographicsResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductHistoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.ProductService;
import peaksoft.house.gadgetariumb9.services.SubProductHistory;
import peaksoft.house.gadgetariumb9.services.SubProductService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Products API", description = "API for product CRUD management")
public class ProductApi {

    private final ProductService productService;

    private final SubProductService subProductService;

    private final SubProductHistory subProductHistory;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "save Product", description = "Creating a new product")
    @PostMapping
    public SimpleResponse save(@RequestBody ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @GetMapping("/colors/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Get color", description = "Get the right colors")
    public List<String> getColors(@PathVariable String name) {
        return productService.getColor(name);
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter catalog", description = "Method for filtering products")
    @PermitAll
    public SubProductPagination filter(
            @RequestBody SubProductCatalogRequest subProductCatalogRequest,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(defaultValue = "1") int pageNumber) {
        return subProductService.getSubProductCatalogs(subProductCatalogRequest, pageSize, pageNumber);
    }

    @GetMapping("get-by-sub-product-id/{sub-product-id}")
    @Operation(summary = "Get sub product", description = "Get sub product by id id")
    @PreAuthorize("hasAuthority('USER')")
    public void getSubProductId(@PathVariable("sub-product-id") Long productId) {
        subProductHistory.addRecentlyViewedProduct(productId);
    }

    @GetMapping("/info")
    @Operation(summary = "Get infographics", description = "Getting infographics of orders for all time")
    @PreAuthorize("hasAuthority('ADMIN')")
    public InfographicsResponse infographics(@RequestParam(defaultValue = "day") String period) {
        return subProductService.infographics(period);
    }

    @GetMapping("/recently-viewed")
    @Operation(summary = "Get products by recently viewed", description = "Browsing history method")
    @PreAuthorize("hasAuthority('USER')")
    public List<SubProductHistoryResponse> getRecentlyViewedProducts() {
        return subProductHistory.getRecentlyViewedProduct();
    }
}
