package peaksoft.house.gadgetariumb9.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.brand.BrandRequest;
import peaksoft.house.gadgetariumb9.dto.response.brand.BrandResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.AlreadyExistException;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.Brand;
import peaksoft.house.gadgetariumb9.repositories.BrandRepository;
import peaksoft.house.gadgetariumb9.services.BrandService;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public SimpleResponse saveBrand(BrandRequest brandRequest) {
        if (brandRepository.existsByName(brandRequest.getName())) {
            log.error("Brand name  already exists");
            throw new AlreadyExistException("Brand with name:  " + brandRequest.getName() + " already exists");
        }
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setImage(brandRequest.getImage());
        brandRepository.save(brand);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Brand with name : %s successfully saved ...!", brandRequest.getName()))
                .build();
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        List<BrandResponse> brands = brandRepository.getAllBrands();
        if (brands.isEmpty()) {
            throw new NotFoundException("No brands found");
        }
        return brands;
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new NotFoundException(String.format("Brand with id: %d not found", id));
        }
        brandRepository.deleteById(id);
        log.info("Successfully deleted");
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Brand with id: %d is deleted", id))
                .build();
    }
}