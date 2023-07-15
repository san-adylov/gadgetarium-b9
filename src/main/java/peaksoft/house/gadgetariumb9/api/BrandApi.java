package peaksoft.house.gadgetariumb9.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.brand.BrandRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.service.impl.BrandServiceImpl;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@Tag(name = "Brand")
public class BrandApi {

    private final BrandServiceImpl brandService;

    @Operation(summary = "brandSave")
    @PostMapping("/save")
    public SimpleResponse saveBrand(@RequestBody @Valid BrandRequest brandRequest) {
        return brandService.saveBrand(brandRequest);
    }

}
