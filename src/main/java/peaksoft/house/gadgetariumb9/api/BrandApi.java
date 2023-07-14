package peaksoft.house.gadgetariumb9.api;

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
public class BrandApi {

    private final BrandServiceImpl brandService;

    @PostMapping("/save")
    public SimpleResponse saveBrand(@RequestBody BrandRequest brandRequest) {
        return brandService.saveBrand(brandRequest);
    }

}
