package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.product.ProductRequest;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.InfographicsResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.MainPagePaginationResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductHistoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPaginationCatalogAdminResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.ProductService;
import peaksoft.house.gadgetariumb9.services.SubProductService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
        return subProductService.getNewProducts(page,pageSize);
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

   /* @GetMapping
    @Operation(summary = "Get all subProduct", description = "Displaying the total number of subProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SubProductPaginationCatalogAdminResponse getAll(
            @RequestParam(defaultValue = "Все товары", required = false) String productType,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate) {

        return subProductService.getGetAllSubProductAdmin(productType, startDate, endDate, pageSize, pageNumber);
    }*/

    @GetMapping("/api/subProducts")
    @Operation(summary = "Get all subProduct", description = "Displaying the total number of subProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SubProductPaginationCatalogAdminResponse> getAllSubProductsAdmin(
            @RequestParam(required = false) String productTyp,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(defaultValue = "1") int pageNumber
    ) {
        SubProductPaginationCatalogAdminResponse response = subProductService.getGetAllSubProductAdmin(
                productTyp, startDate, endDate, pageSize, pageNumber
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


@DeleteMapping("/single-delete/{subProductId}")
    @Operation(summary = "single delete get by subProductId",description = "single delete subProduct get by subProductId")
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
        return subProductService.updateSubProduct(id,productRequest);
    }
}
