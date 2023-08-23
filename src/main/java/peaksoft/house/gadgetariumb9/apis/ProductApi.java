package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.product.ProductRequest;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.compare.CompareProductResponse;
import peaksoft.house.gadgetariumb9.dto.response.compare.ComparisonCountResponse;
import peaksoft.house.gadgetariumb9.dto.response.product.ProductUserAndAdminResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.*;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.ProductService;
import peaksoft.house.gadgetariumb9.services.SubProductService;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Products API", description = "API for product CRUD management")
public class ProductApi {

    private final ProductService productService;
    private final SubProductService subProductService;

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

    @GetMapping("/new")
    @Operation(summary = "Get new products", description = "This method gets new products")
    public MainPagePaginationResponse getNewProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int pageSize) {
        return subProductService.getNewProducts(page, pageSize);
    }

    @GetMapping("/recommended")
    @Operation(summary = "Get recommended products", description = "This method gets recommended products")
    public MainPagePaginationResponse getRecommendedProducts(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "5") int pageSize) {
        return subProductService.getRecommendedProducts(page, pageSize);
    }

    @GetMapping("/discount")
    @Operation(summary = "Get discount products", description = "This method gets all discount products")
    public MainPagePaginationResponse getAllDiscountProducts(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "5") int pageSize) {
        return subProductService.getAllDiscountProducts(page, pageSize);

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

    @GetMapping("/get-product/{sub-product-id}")
    @Operation(summary = "Get sub product", description = "Get sub product by id id")
    @PreAuthorize("hasAuthority('USER')")
    public void getSubProductId(@PathVariable("sub-product-id") Long productId) {
        subProductService.addRecentlyViewedProduct(productId);
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
        return subProductService.getRecentlyViewedProduct();
    }

    @GetMapping
    @Operation(summary = "Get all subProduct", description = "Displaying the total number of subProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SubProductPaginationCatalogAdminResponse getAll(@RequestParam(defaultValue = "Все товары", required = false) String productType,
                                                           @RequestParam(name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                           @RequestParam(name = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                                           @RequestParam(defaultValue = "6") int pageSize,
                                                           @RequestParam(defaultValue = "1") int pageNumber) {
        return subProductService.getGetAllSubProductAdmin(productType, startDate, endDate, pageSize, pageNumber);
    }

    @DeleteMapping("/single-delete/{subProductId}")
    @Operation(summary = "single delete get by subProductId", description = "single delete subProduct get by subProductId")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse singleDeleteSubProduct(@PathVariable Long subProductId) {
        return subProductService.singleDelete(subProductId);
    }

    @DeleteMapping("/multi-delete")
    @Operation(summary = "multi delete get by subProductId", description = "multi delete subProduct get by subProductId")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse multiDeleteSubProduct(@RequestBody List<Long> subProductId) {
        return subProductService.multiDelete(subProductId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "edit get by id", description = "The method updates the object")
    @PutMapping("/{id}")
    public SimpleResponse editSubProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return subProductService.updateSubProduct(id, productRequest);
    }

    @GetMapping("/get-by-id")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @Operation(summary = "To get by product id the product.", description = "This method to get by product id the product.")
    public ProductUserAndAdminResponse getByProductId(@RequestParam Long productId,
                                                      @RequestParam(defaultValue = "", required = false) String colour) {
        return productService.getByProductId(productId, colour);
    }

    @GetMapping("/count/{userId}/comparison")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get comparison data for a specific user", description = "Retrieves the comparison data for a user with the provided user ID.")
    public List<ComparisonCountResponse> countCompareUser(@PathVariable Long userId) {
        return subProductService.countCompareUser(userId);
    }

    @PostMapping("/save-comparison")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Save or delete product comparisons for a user", description = "Allows a user to save or delete product comparisons based on the provided parameters.")
    public SimpleResponse saveComparisonsUser(@RequestParam Long id, @RequestParam boolean addOrDelete) {
        return subProductService.comparisonAddOrDelete(id, addOrDelete);
    }

    @Operation(summary = "Compare product parameters", description = "Retrieve a list of products with their parameters based on the provided product name.")
    @GetMapping("/compare-product")
    @PreAuthorize("hasAuthority('USER')")
    public List<CompareProductResponse> compareParameters(@RequestParam String productName) {
        return subProductService.getCompareParameters(productName);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(
            summary = "Clear user's product comparisons",
            description = "Clears all product comparisons for the authenticated user.")
    public SimpleResponse cleanCompare() {
        return subProductService.clearUserCompare();
    }
}
