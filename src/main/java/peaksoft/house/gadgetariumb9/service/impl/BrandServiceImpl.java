package peaksoft.house.gadgetariumb9.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.brand.BrandRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.entities.Brand;
import peaksoft.house.gadgetariumb9.exception.AlreadyExistException;
import peaksoft.house.gadgetariumb9.repository.BrandRepository;
import peaksoft.house.gadgetariumb9.service.BrandService;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public SimpleResponse saveBrand(BrandRequest brandRequest) {
        if (brandRepository.existsByName(brandRequest.getName())) {
            throw new AlreadyExistException("Brand with name : %s already exists" + brandRequest.getName());
        }
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setImage(brandRequest.getImage());
        log.info("Brand successfully saved ...!");
        brandRepository.save(brand);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Brand with name : %s successfully saved ...!", brandRequest.getImage()))
                .build();
    }
}
