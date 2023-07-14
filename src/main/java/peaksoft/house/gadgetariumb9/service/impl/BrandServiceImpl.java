package peaksoft.house.gadgetariumb9.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.brand.BrandRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.entities.Brand;
import peaksoft.house.gadgetariumb9.repository.BrandRepository;
import peaksoft.house.gadgetariumb9.s3file.service.S3FileService;
import peaksoft.house.gadgetariumb9.service.BrandService;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final S3FileService s3File;


    @Override
    public SimpleResponse saveBrand(BrandRequest brandRequest) {
        if (brandRepository.existsByName(brandRequest.name())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("Restaurant with name : %s already exists", brandRequest.name()))
                    .build();
        }
        Brand brand = new Brand();
        brand.setName(brandRequest.name());
        brand.setImage(s3File.uploadFile(brandRequest.image()));
        brandRepository.save(brand);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Brand with name : %s successfully saved ...!", brandRequest.name()))
                .build();
    }
}
