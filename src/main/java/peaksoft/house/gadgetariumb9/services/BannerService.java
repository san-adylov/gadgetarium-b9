package peaksoft.house.gadgetariumb9.service;

import peaksoft.house.gadgetariumb9.dto.request.banner.BannerRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface BannerService {

    SimpleResponse saveBanner(BannerRequest bannerRequest);
}
