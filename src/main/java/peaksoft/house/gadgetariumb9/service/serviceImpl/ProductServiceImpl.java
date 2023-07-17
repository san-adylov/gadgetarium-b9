package peaksoft.house.gadgetariumb9.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.productRequest.ProductRequest;
import peaksoft.house.gadgetariumb9.entities.*;
import peaksoft.house.gadgetariumb9.exception.NotFoundException;
import peaksoft.house.gadgetariumb9.repository.ProductRepository;
import peaksoft.house.gadgetariumb9.service.ProductService;

import java.time.ZonedDateTime;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    private final SubCategoryRepository subCategoryRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public SimpleResponse saveProduct(ProductRequest productRequest) {

        SubCategory subCategory = subCategoryRepository.findById(productRequest.getSubCategoryId())
                .orElseThrow(() -> new NotFoundException("SubCategory with id: " + productRequest.getSubCategoryId() + "is not found"));

        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new NotFoundException("Brand with id: " + productRequest.getBrandId() + "is not found"));


        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category with id: " + productRequest.getCategoryId() + "is not found"));
        Product product = Product.builder()
                .subCategory(subCategory)
                .brand(brand)
                .category(category)
                .name(productRequest.getName())
                .dataOfIssue(ZonedDateTime.now())
                .createdAt(ZonedDateTime.now())
                .guarantee(productRequest.getGuarantee())
                .build();
        productRepository.save(product);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("successfully added")
                .build();
    }
}
