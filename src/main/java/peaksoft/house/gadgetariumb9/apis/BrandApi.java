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
@RequestMapping("api/brand")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "Brand", description = "API for working with brand")
public class BrandApi {

    private final BrandServiceImpl brandService;

    @Operation(summary = "Save brand", description = "Preservation of the new brand")
    @PostMapping("/save")
    public SimpleResponse saveBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return brandService.saveBrand(brandRequest);
    }

    @Operation(summary = "Get all", description = "Getting all brands")
    @GetMapping("/get-all")
    public List<BrandResponse> getAllBrands() {
        return brandService.getAllBrands();
    }

    @Operation(summary = "brand delete", description = "Removing a brand")
    @DeleteMapping("/{id}/delete")
    public SimpleResponse deleteById(@PathVariable Long id) {
        return brandService.deleteById(id);
    }
}
