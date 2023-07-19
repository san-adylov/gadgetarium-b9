package peaksoft.house.gadgetariumb9.service.serviceImpl;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.color.CodeColor;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.productRequest.ProductRequest;
import peaksoft.house.gadgetariumb9.entities.*;
import peaksoft.house.gadgetariumb9.exception.NotFoundException;
import peaksoft.house.gadgetariumb9.service.ProductService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CodeColor codeColor;

    @Override
    public SimpleResponse saveProduct(ProductRequest productRequest) {
        SubCategory subCategory = subCategoryRepository.findById(productRequest.getSubCategoryId())
                .orElseThrow(() -> {
                    log.error("SubCategory with id: " + productRequest.getSubCategoryId() + " is not found");
                    return new NotFoundException("SubCategory with id: " + productRequest.getSubCategoryId() + " is not found");
                });

        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> {
                    log.error("Brand with id: " + productRequest.getBrandId() + " is not found");
                    return new NotFoundException("Brand with id: " + productRequest.getBrandId() + " is not found");
                });

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> {
                    log.error("Category with id: " + productRequest.getCategoryId() + " is not found");
                    return new NotFoundException("Category with id: " + productRequest.getCategoryId() + " is not found");
                });

        Product product = new Product();

        List<SubProduct> subProducts = productRequest.getSubProductRequests().stream()
                .map(subProductRequest -> {
                    SubProduct subProduct = new SubProduct();
                    subProduct.setCodeColor(subProductRequest.getCodeColor());
                    subProduct.setRom(subProductRequest.getRom());
                    subProduct.setRam(subProductRequest.getRam());
                    subProduct.setScreenResolution(subProductRequest.getScreenResolution());
                    subProduct.setAdditionalFeatures(subProductRequest.getAdditionalFeatures());
                    subProduct.setQuantity(subProductRequest.getQuantity());
                    subProduct.setImages(subProductRequest.getImages());
                    subProduct.setPrice(subProductRequest.getPrice());
                    int art = generate();
                    while (subProduct.getArticleNumber() != art) {
                        subProduct.setArticleNumber(art);
                        subProduct.setProduct(product);
                        product.setSubProducts(List.of(subProduct));
                    }

                    log.info("SubProduct saved successfully");
                    subProductRepository.save(subProduct);

                    if (category.getTitle().equalsIgnoreCase("Laptop")) {
                        Laptop laptop = new Laptop();
                        laptop.setProcessor(subProductRequest.getProcessor());
                        laptop.setPurpose(subProductRequest.getPurpose());
                        laptop.setVideoMemory(subProductRequest.getVideoMemory());
                        laptop.setScreenSize(subProductRequest.getScreenSize());
                        subProduct.setLaptop(laptop);
                        laptop.setSubProduct(subProduct);

                        log.info("Laptop saved successfully");
                        laptopRepository.save(laptop);

                    } else if (category.getTitle().equalsIgnoreCase("Smartphone") || category.getTitle().equalsIgnoreCase("Tablet")) {
                        Phone phone = new Phone();
                        phone.setSim(subProductRequest.getSim());
                        phone.setScreenSize(subProductRequest.getScreenSize());
                        phone.setDiagonalScreen(subProductRequest.getDiagonalScreen());
                        phone.setBatteryCapacity(subProductRequest.getBatteryCapacity());
                        subProduct.setPhone(phone);
                        phone.setSubProduct(subProduct);

                        log.info("phone saved successfully");
                        phoneRepository.save(phone);

                    } else if (category.getTitle().equalsIgnoreCase("Smart watch")) {
                        SmartWatch smartWatch = new SmartWatch();
                        smartWatch.setAnInterface(subProductRequest.getAnInterface());
                        smartWatch.setHUllShape(subProductRequest.getHullShape());
                        smartWatch.setMaterialBracelet(subProductRequest.getMaterialBracelet());
                        smartWatch.setHousingMaterial(subProductRequest.getHousingMaterial());
                        smartWatch.setGender(subProductRequest.getGender());
                        smartWatch.setWaterproof(subProductRequest.isWaterproof());
                        smartWatch.setDisplayDiscount(subProductRequest.getDisplayDiscount());
                        subProduct.setSmartWatch(smartWatch);
                        smartWatch.setSubProduct(subProduct);

                        log.info("SmartWatch saved successfully");
                        smartWatchRepository.save(smartWatch);

                    }
                    return subProduct;
                })
                .collect(Collectors.toList());

        Product product2 = Product.builder()
                .subCategory(subCategory)
                .brand(brand)
                .category(category)
                .name(productRequest.getName())
                .dataOfIssue(productRequest.getDateOfIssue())
                .createdAt(ZonedDateTime.now())
                .guarantee(productRequest.getGuarantee())
                .subProducts(subProducts)
                .build();

        log.info("Product saved successfully");
        productRepository.save(product2);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully added")
                .build();
    }

    private int generate() {
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }

    public List<String> getColor() {
        return new ArrayList<>(codeColor.getColorMap().keySet());
    }
}
