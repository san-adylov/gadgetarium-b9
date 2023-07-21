package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.banner.BannerRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.serviceImpl.BannerServiceImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/banners")
@Tag(name = "Banner API", description = "API for banner management")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BannerApi {

        private final BannerServiceImpl bannerService;

        @PreAuthorize("hasAuthority('ADMIN')")
        @Operation(summary = "Save Banner",description = "This is a method of saving banners")
        @PostMapping()
        SimpleResponse saveBanners(@RequestBody @Valid BannerRequest bannerRequest) {
            return bannerService.saveBanners(bannerRequest);
        }
    }


