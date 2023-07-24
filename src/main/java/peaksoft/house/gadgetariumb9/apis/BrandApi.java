package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.brand.BrandRequest;
import peaksoft.house.gadgetariumb9.dto.response.brand.BrandResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.serviceImpl.BrandServiceImpl;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brands")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Brand API", description = "All brand endpoints")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BrandApi {

    private final BrandServiceImpl brandService;

    @Operation(summary = "Add brand", description = "Add new brand")
    @PostMapping
    public SimpleResponse saveBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return brandService.saveBrand(brandRequest);
    }

    @Operation(summary = "Get all brands", description = "Get all brands")
    @GetMapping
    public List<BrandResponse> getAllBrands() {
        return brandService.getAllBrands();
    }

    @Operation(summary = "Delete brand", description = "Delete brand by id")
    @DeleteMapping("/{brandId}")
    public SimpleResponse deleteById(@PathVariable Long brandId) {
        return brandService.deleteById(brandId);
    }
}