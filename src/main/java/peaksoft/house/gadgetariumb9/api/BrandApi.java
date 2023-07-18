package peaksoft.house.gadgetariumb9.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.brand.BrandRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.service.serviceImpl.BrandServiceImpl;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@Tag(name = "Brand")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BrandApi {

    private final BrandServiceImpl brandService;

    @Operation(summary = "brandSave")
    @PostMapping("/save")
    public SimpleResponse saveBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return brandService.saveBrand(brandRequest);
    }

}
