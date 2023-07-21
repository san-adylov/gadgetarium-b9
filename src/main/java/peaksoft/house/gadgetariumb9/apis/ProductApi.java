package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.productRequest.ProductRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin(maxAge = 3600 , origins = "*")
@Tag(name = "Products",description = "Here we create a product and different methods work")
public class ProductApi {

    private final ProductService productService;

    @PostMapping("/save-product")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "save Product", description = "Here we create different products")
    public SimpleResponse save(@RequestBody ProductRequest productRequest) {
        return productService.saveProduct(productRequest);
    }

    @GetMapping("/colors{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @Operation(summary = "Get color", description = "Here we get gentle colors")
    public List<String> getColors(@PathVariable String name){
        return productService.getColor(name);
    }
}
