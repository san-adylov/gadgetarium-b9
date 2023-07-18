package peaksoft.house.gadgetariumb9.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.productRequest.ProductRequest;
import peaksoft.house.gadgetariumb9.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600 , origins = "*")
@Tag(name = "Products")
public class ProductApi {

    private final ProductService productService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "save Product", description = "save")
    public SimpleResponse save(@RequestBody ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }
}
