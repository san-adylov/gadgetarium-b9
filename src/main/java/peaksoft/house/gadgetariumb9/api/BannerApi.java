package peaksoft.house.gadgetariumb9.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.banner.BannerRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.service.impl.BannerServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/banners")
public class BannerApi {


    private final BannerServiceImpl bannerService;

    @PostMapping("/save")
    SimpleResponse saveBanners(@RequestBody BannerRequest bannerRequest){
       return bannerService.saveBanner(bannerRequest);
    }

}
