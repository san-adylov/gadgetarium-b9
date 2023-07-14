package peaksoft.house.gadgetariumb9.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import peaksoft.house.gadgetariumb9.dto.request.brand.BrandRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.service.impl.BrandServiceImpl;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandApi {

    private final BrandServiceImpl brandService;

    @PostMapping("/save")
    public SimpleResponse save(@RequestParam String brand, @RequestPart MultipartFile file) {
        return brandService.saveBrand(BrandRequest
                .builder()
                .name(brand)
                .image(file)
                .build());
    }

}
