package peaksoft.house.gadgetariumb9.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.banner.BannerRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.InvalidBannerException;
import peaksoft.house.gadgetariumb9.models.Banner;
import peaksoft.house.gadgetariumb9.repositories.BannerRepository;
import peaksoft.house.gadgetariumb9.services.BannerService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannerServiceImpl implements BannerService {


    private final BannerRepository bannerRepository;

    @Override
    public SimpleResponse saveBanners(BannerRequest bannerRequest) {
        List<String> images = new ArrayList<>();
        for (String b : bannerRequest.getBannerImages()) {
            if (b.isBlank() || b.isEmpty()) {
                throw new InvalidBannerException("No valid banners to save!");
            }
            validateBanners(bannerRequest.getBannerImages());
            Banner banner = new Banner();
            banner.setImages(images);
            banner.getImages().addAll(bannerRequest.getBannerImages());
            bannerRepository.save(banner);
            log.info("Successfully saved banners");
            return SimpleResponse.builder()
                    .message("Successfully saved banners")
                    .status(HttpStatus.OK)
                    .build();

        }
        return null;
    }

    private void validateBanners(List<String> images) {
        if (images.size() > 6 || images.stream().anyMatch(String::isBlank)) {
            throw new InvalidBannerException("Invalid banners. The number of banners should not exceed 6 or contain empty elements!");
        }
    }

}


