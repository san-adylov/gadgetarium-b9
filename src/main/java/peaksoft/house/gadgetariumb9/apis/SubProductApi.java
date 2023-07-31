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
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPaginationCatalogAdminResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.SubProductService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subProduct")
@Tag(name = "Sub product", description = "API for working with sub product")
public class SubProductApi {

    private final SubProductService subProductService;
    @PostMapping("/filter")
    @Operation(summary = "Filter catalog", description = "Method for filtering products")
    @PermitAll
    public SubProductPagination filter(
            @RequestBody SubProductCatalogRequest subProductCatalogRequest,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(defaultValue = "1") int pageNumber) {
        return subProductService.getSubProductCatalogs(subProductCatalogRequest, pageSize, pageNumber);
    }

    @GetMapping("/info")
    @Operation(summary = "Get infographics", description = "Getting infographics of orders for all time")
    @PreAuthorize("hasAuthority('ADMIN')")
    public InfographicsResponse infographics(@RequestParam(defaultValue = "day") String period) {
        return subProductService.infographics(period);
    }

    @GetMapping
    @Operation(summary = "Get all subProduct", description = "Method sorts and all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SubProductPaginationCatalogAdminResponse getAll(@RequestParam(defaultValue = "Все товары", required = false) String productType,
                                                           @RequestParam(defaultValue = "6") int pageSize,
                                                           @RequestParam(defaultValue = "1") int pageNumber) {
        return subProductService.getGetAllSubProductAdmin(productType, pageSize, pageNumber);
    }

    @DeleteMapping("/single-delete/{subProductId}")
    @Operation(summary = "single delete ",description = "delete one by one subProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse singleDeleteSubProduct(@PathVariable Long subProductId) {
        return subProductService.singleDelete(subProductId);
    }

    @DeleteMapping("/multi-delete")
    @Operation(summary = " multi delete",description = "selectively removes subProduct")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse multiDeleteSubProduct(@RequestBody List<Long> subProductId) {
        return subProductService.multiDelete(subProductId);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "edit", description = "The method updates the object")
    @PutMapping("/{id}")
    public SimpleResponse editSubProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return subProductService.updateSubProduct(id,productRequest);
    }
}
