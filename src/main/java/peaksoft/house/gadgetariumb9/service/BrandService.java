package peaksoft.house.gadgetariumb9.service;

import peaksoft.house.gadgetariumb9.dto.request.brand.BrandRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface BrandService {

    SimpleResponse saveBrand(BrandRequest brandRequest);
}
