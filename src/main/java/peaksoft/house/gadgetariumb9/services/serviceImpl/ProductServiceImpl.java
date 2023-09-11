package peaksoft.house.gadgetariumb9.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.product.ProductRequest;
import peaksoft.house.gadgetariumb9.dto.response.product.ProductUserAndAdminResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.*;
import peaksoft.house.gadgetariumb9.models.color.CodeColor;
import peaksoft.house.gadgetariumb9.repositories.*;
import peaksoft.house.gadgetariumb9.services.ProductService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import peaksoft.house.gadgetariumb9.template.ProductTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final SubCategoryRepository subCategoryRepository;

    private final SubProductRepository subProductRepository;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    private final LaptopRepository laptopRepository;

    private final PhoneRepository phoneRepository;

    private final SmartWatchRepository smartWatchRepository;

    private final ProductTemplate productTemplate;

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

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime startDateZ = ZonedDateTime.of(productRequest.getDateOfIssue().atStartOfDay(), zoneId);
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime modifiedDateTime = now.withNano(0);
        Product product = new Product();
        product.setCategory(category);
        product.setSubCategory(subCategory);
        product.setBrand(brand);
        product.setName(productRequest.getName());
        product.setGuarantee(productRequest.getGuarantee());
        product.setDataOfIssue(startDateZ);
        product.setCreatedAt(modifiedDateTime);

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
                    subProduct.setPrice(subProductRequest.getPrice());
                    subProduct.setQuantity(subProductRequest.getQuantity());

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
                        smartWatch.setHullShape(subProductRequest.getHullShape());
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

        product.setSubProducts(subProducts);
        product.setVideoLink(productRequest.getVideoLink());
        product.setPdf(productRequest.getPdf());
        product.setDescription(productRequest.getDescription());

        log.info("Product saved successfully");
        productRepository.save(product);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully added")
                .build();
    }

    private int generate() {
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }

    public List<String> getColor(String name) {
        return codeColor.ColorName(name);
    }

  @Override
  public ProductUserAndAdminResponse getByProductId(Long productId, String color) {
    productRepository.findById(productId).orElseThrow(
        () -> {
          log.error("Product with id: " + productId + " is not found");
          return new NotFoundException("Product with id: " + productId + " is not found");
        });
    return productTemplate.getByProductId(productId,color);
  }
}
