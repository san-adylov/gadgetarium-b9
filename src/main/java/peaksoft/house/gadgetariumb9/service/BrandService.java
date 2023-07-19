package peaksoft.house.gadgetariumb9.service;

import peaksoft.house.gadgetariumb9.dto.request.brand.BrandRequest;
import peaksoft.house.gadgetariumb9.dto.response.brand.BrandResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import java.util.List;

public interface BrandService {

    SimpleResponse saveBrand(BrandRequest brandRequest);

    List<BrandResponse> getAllBrands();

    SimpleResponse deleteById(Long id);
}
