package peaksoft.house.gadgetariumb9.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.brand.BrandRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.entities.Brand;
import peaksoft.house.gadgetariumb9.entities.User;
import peaksoft.house.gadgetariumb9.enums.Role;
import peaksoft.house.gadgetariumb9.exception.AlreadyExistException;
import peaksoft.house.gadgetariumb9.exception.NotFoundException;
import peaksoft.house.gadgetariumb9.repository.BrandRepository;
import peaksoft.house.gadgetariumb9.service.BrandService;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final JwtService jwtService;


    @Override
    public SimpleResponse saveBrand(BrandRequest brandRequest) {
        if (brandRepository.existsByName(brandRequest.getName())) {
            throw new AlreadyExistException("Brand with name : %s already exists" + brandRequest.getName());
        }
        User authentication = jwtService.getAuthentication();
        if(!authentication.getRole().equals(Role.ADMIN)){
            throw new NotFoundException("THE ADMIN does not exist...");
        }
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setImage(brandRequest.getImage());
        brandRepository.save(brand);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Brand with name : %s successfully saved ...!", brandRequest.getImage()))
                .build();
    }
}


