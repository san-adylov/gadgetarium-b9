package peaksoft.house.gadgetariumb9.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.banner.BannerRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.entities.Banner;
import peaksoft.house.gadgetariumb9.repository.BannerRepository;
import peaksoft.house.gadgetariumb9.service.BannerService;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    @Override
    public SimpleResponse saveBanner(BannerRequest bannerRequest) {
        List<String> bannerImages = bannerRequest.getBannerImages();

        if (bannerImages == null) {
            return SimpleResponse.builder()
                    .message("No banner images provided")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        validateBanners(bannerImages);

        for (String bannerImage : bannerImages) {
            Banner banner = new Banner();
            banner.setImages(Collections.singletonList(bannerImage));
            bannerRepository.save(banner);
        }

        log.info("Successfully saved banners");

        return SimpleResponse.builder()
                .message("Successfully saved banners")
                .httpStatus(HttpStatus.OK)
                .build();
    }

    private void validateBanners(List<String> images) {
        if (images.size() > 6 || images.stream().anyMatch(String::isBlank)) {
            throw new RuntimeException("The number of banners should not exceed 6 or contain empty elements!");
        }
    }
}


