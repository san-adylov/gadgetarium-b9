package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.request.banner.BannerRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.models.Banner;

import java.util.List;

public interface BannerService {
    SimpleResponse saveBanners(BannerRequest bannerRequest);


    List<Banner> getAllBanner();
}
