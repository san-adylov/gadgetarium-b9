package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.request.product.ProductRequest;
import peaksoft.house.gadgetariumb9.dto.request.subProduct.SubProductCatalogRequest;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.InfographicsResponse;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPagination;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductPaginationCatalogAdminResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.BadRequestException;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.*;
import peaksoft.house.gadgetariumb9.repositories.*;
import peaksoft.house.gadgetariumb9.services.SubProductService;
import peaksoft.house.gadgetariumb9.template.SubProductTemplate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubProductServiceImpl implements SubProductService {

    private final SubProductTemplate subProductTemplate;

    private final SubProductRepository subProductRepository;

    private final DiscountRepository discountRepository;

    private final LaptopRepository laptopRepository;

    private final PhoneRepository phoneRepository;

    private final SmartWatchRepository smartWatchRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;


    @Override
    public SubProductPagination getSubProductCatalogs(
            SubProductCatalogRequest subProductCatalogRequest, int pageSize, int pageNumber) {
        log.info("Filter sub product");
        return subProductTemplate.getProductFilter(subProductCatalogRequest, pageSize, pageNumber);
    }

    @Override
    public InfographicsResponse infographics(String period) {
        return subProductTemplate.infographics(period);
    }

    @Override
    public SubProductPaginationCatalogAdminResponse getGetAllSubProductAdmin(String productType, int pageSize, int pageNumber) {
        return subProductTemplate.getGetAllSubProductAdmin(productType,
                pageSize, pageNumber);
    }

    @Override
    @Transactional
    public SimpleResponse singleDelete(Long subProductId) {
        SubProduct subProduct = subProductRepository.findById(subProductId)
                .orElseThrow(() -> {
                    log.error("SubProduct with id: " + subProductId + " is not found");
                    return new NotFoundException("SubProduct with id: " + subProductId + " is not found");
                });

        subProduct.getBaskets().forEach(basket -> basket.getSubProducts().remove(subProduct));
        subProduct.getOrders().forEach(order -> order.getSubProducts().remove(subProduct));
        subProduct.getReviews().remove(subProduct);

        if (subProduct.getDiscount() != null) {
            discountRepository.delete(subProduct.getDiscount());
        }
        if (subProduct.getLaptop() != null) {
            laptopRepository.delete(subProduct.getLaptop());
        }
        if (subProduct.getPhone() != null) {
            phoneRepository.delete(subProduct.getPhone());
        }
        if (subProduct.getSmartWatch() != null) {
            smartWatchRepository.delete(subProduct.getSmartWatch());
        }

        userRepository.findAll().forEach(user -> {
            List<Long> favorites = user.getFavorite();
            if (favorites != null) {
                favorites.remove(subProduct.getId());
            }
        });

        subProductRepository.delete(subProduct);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("SubProduct with id: %d is deleted", subProductId))
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse multiDelete(List<Long> subProductIds) {
        for (Long id : subProductIds) {
            SubProduct subProduct = subProductRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("SubProduct with id: " + id + " is not found");
                        return new NotFoundException("SubProduct with id: " + id + " is not found");
                    });

            subProduct.getBaskets().forEach(basket -> basket.getSubProducts().remove(subProduct));
            subProduct.getOrders().forEach(order -> order.getSubProducts().remove(subProduct));
            subProduct.getReviews().remove(subProduct);

            if (subProduct.getDiscount() != null) {
                discountRepository.delete(subProduct.getDiscount());
            }
            if (subProduct.getLaptop() != null) {
                laptopRepository.delete(subProduct.getLaptop());
            }
            if (subProduct.getPhone() != null) {
                phoneRepository.delete(subProduct.getPhone());
            }
            if (subProduct.getSmartWatch() != null) {
                smartWatchRepository.delete(subProduct.getSmartWatch());
            }

            userRepository.findAll().forEach(user -> {
                List<Long> favorites = user.getFavorite();
                if (favorites != null) {
                    favorites.remove(subProduct.getId());
                }
            });

            subProductRepository.deleteById(subProduct.getId());
        }

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("SubProducts with given IDs are deleted")
                .build();
    }

    @Override
    public SimpleResponse updateSubProduct(Long subProductId, ProductRequest productRequest) {
        SubProduct oldSubProduct = subProductRepository.findById(subProductId).orElseThrow(() -> {
            log.error("Sub product with id:" + subProductId + " is not found!");
            return new NotFoundException("Подпродукт с id: " + subProductId + " не найден!");
        });

        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow();
        switch (category.getTitle()) {
            case "Laptop" -> {
                if (Objects.equals(oldSubProduct.getId(), subProductId)) {
                    productRequest.getSubProductRequests().forEach(x -> {
                        if (x.getPurpose() != null && !x.getPurpose().equals(oldSubProduct.getLaptop().getPurpose())) {
                            oldSubProduct.getLaptop().setPurpose(x.getPurpose());
                        } else {
                            log.error("Invalid or unchanged purpose value provided.");
                            throw new BadRequestException("Invalid or unchanged purpose value provided.");
                        }

                        if (x.getProcessor() != null && !x.getProcessor().equals(oldSubProduct.getLaptop().getProcessor())) {
                            oldSubProduct.getLaptop().setProcessor(x.getProcessor());
                        } else {
                            log.error("Invalid or unchanged processor value provided.");
                            throw new BadRequestException("Invalid or unchanged processor value provided.");
                        }

                        if (x.getVideoMemory() != 0 && x.getVideoMemory() != oldSubProduct.getLaptop().getVideoMemory()) {
                            oldSubProduct.getLaptop().setVideoMemory(x.getVideoMemory());
                        } else {
                            log.error("Invalid or unchanged video memory value provided.");
                            throw new BadRequestException("Invalid or unchanged video memory value provided.");
                        }

                        if (x.getScreenSize() != 0 && oldSubProduct.getLaptop().getScreenSize() != x.getScreenSize()) {
                            oldSubProduct.getLaptop().setScreenSize(x.getScreenSize());
                        } else {
                            log.error("Invalid or unchanged screen size value provided.");
                            throw new BadRequestException("Invalid or unchanged screen size value provided.");
                        }

                        if (x.getRam() != 0 && oldSubProduct.getRam() != x.getRam()) {
                            oldSubProduct.setRam(x.getRam());
                        } else {
                            log.error("Invalid or unchanged RAM value provided.");
                            throw new BadRequestException("Invalid or unchanged RAM value provided.");
                        }

                        if (x.getScreenResolution() != null && !x.getScreenResolution().equals(oldSubProduct.getScreenResolution())) {
                            oldSubProduct.setScreenResolution(x.getScreenResolution());
                        } else {
                            log.error("Invalid or unchanged screen resolution value provided.");
                            throw new BadRequestException("Invalid or unchanged screen resolution value provided.");
                        }

                        if (x.getRom() != 0 && oldSubProduct.getRom() != x.getRom()) {
                            oldSubProduct.setRom(x.getRom());
                        } else {
                            log.error("Invalid or unchanged ROM value provided.");
                            throw new BadRequestException("Invalid or unchanged ROM value provided.");
                        }

                        if (x.getAdditionalFeatures() != null && !x.getAdditionalFeatures().equals(oldSubProduct.getAdditionalFeatures())) {
                            oldSubProduct.setAdditionalFeatures(x.getAdditionalFeatures());
                        } else {
                            log.error("Invalid or unchanged additional features provided.");
                            throw new BadRequestException("Invalid or unchanged additional features provided.");
                        }

                        if (x.getPrice() != null && !x.getPrice().equals(oldSubProduct.getPrice())) {
                            oldSubProduct.setPrice(x.getPrice());
                        } else {
                            log.error("Invalid or unchanged price value provided.");
                            throw new BadRequestException("Invalid or unchanged price value provided.");
                        }

                        if (x.getQuantity() != 0 && oldSubProduct.getQuantity() != x.getQuantity()) {
                            oldSubProduct.setQuantity(x.getQuantity());
                        } else {
                            log.error("Invalid or unchanged quantity value provided.");
                            throw new BadRequestException("Invalid or unchanged quantity value provided.");
                        }

                        if (x.getCodeColor() != null && !x.getCodeColor().equals(oldSubProduct.getCodeColor())) {
                            oldSubProduct.setCodeColor(x.getCodeColor());
                        } else {
                            log.error("Invalid or unchanged code color value provided.");
                            throw new BadRequestException("Invalid or unchanged code color value provided.");
                        }

                        if (x.getImages() != null && !x.getImages().equals(oldSubProduct.getImages())) {
                            oldSubProduct.setImages(x.getImages());
                        } else {
                            log.error("Invalid or unchanged images provided.");
                            throw new BadRequestException("Invalid or unchanged images provided.");
                        }

                    });
                }
            }
            case "SmartWatch" -> {
                if (Objects.equals(oldSubProduct.getId(), subProductId)) {
                    productRequest.getSubProductRequests().forEach(x -> {
                        if (x.getAnInterface() != null && !x.getAnInterface().equals(oldSubProduct.getSmartWatch().getAnInterface())) {
                            oldSubProduct.getSmartWatch().setAnInterface(x.getAnInterface());
                        } else {
                            log.error("Invalid or unchanged interface value provided.");
                            throw new BadRequestException("Invalid or unchanged interface value provided.");
                        }

                        if (x.getMaterialBracelet() != null && !x.getMaterialBracelet().equals(oldSubProduct.getSmartWatch().getMaterialBracelet())) {
                            oldSubProduct.getSmartWatch().setMaterialBracelet(x.getMaterialBracelet());
                        } else {
                            log.error("Invalid or unchanged material bracelet value provided.");
                            throw new BadRequestException("Invalid or unchanged material bracelet value provided.");
                        }

                        if (x.getGender() != null && !x.getGender().equals(oldSubProduct.getSmartWatch().getGender())) {
                            oldSubProduct.getSmartWatch().setGender(x.getGender());
                        } else {
                            log.error("Invalid or unchanged gender value provided.");
                            throw new BadRequestException("Invalid or unchanged gender value provided.");
                        }

                        if (x.getDisplayDiscount() != 0 && x.getDisplayDiscount() != oldSubProduct.getSmartWatch().getDisplayDiscount()) {
                            oldSubProduct.getSmartWatch().setDisplayDiscount(x.getDisplayDiscount());
                        } else {
                            log.error("Invalid or unchanged display discount value provided.");
                            throw new BadRequestException("Invalid or unchanged display discount value provided.");
                        }

                        if (x.getHousingMaterial() != null && !x.getHousingMaterial().equals(oldSubProduct.getSmartWatch().getHousingMaterial())) {
                            oldSubProduct.getSmartWatch().setHousingMaterial(x.getHousingMaterial());
                        } else {
                            log.error("Invalid or unchanged housing material value provided.");
                            throw new BadRequestException("Invalid or unchanged housing material value provided.");
                        }

                        if (x.getHullShape() != null && !x.getHullShape().equals(oldSubProduct.getSmartWatch().getHullShape())) {
                            oldSubProduct.getSmartWatch().setHullShape(x.getHullShape());
                        } else {
                            log.error("Invalid or unchanged hull shape value provided.");
                            throw new BadRequestException("Invalid or unchanged hull shape value provided.");
                        }

                        oldSubProduct.getSmartWatch().setWaterproof(x.isWaterproof());

                        if (x.getRam() != 0 && oldSubProduct.getRam() != x.getRam()) {
                            oldSubProduct.setRam(x.getRam());
                        } else {
                            log.error("Invalid or unchanged RAM value provided.");
                            throw new BadRequestException("Invalid or unchanged RAM value provided.");
                        }

                        if (x.getScreenResolution() != null && !x.getScreenResolution().equals(oldSubProduct.getScreenResolution())) {
                            oldSubProduct.setScreenResolution(x.getScreenResolution());
                        } else {
                            log.error("Invalid or unchanged screen resolution value provided.");
                            throw new BadRequestException("Invalid or unchanged screen resolution value provided.");
                        }

                        if (x.getRom() != 0 && oldSubProduct.getRom() != x.getRom()) {
                            oldSubProduct.setRom(x.getRom());
                        } else {
                            log.error("Invalid or unchanged ROM value provided.");
                            throw new BadRequestException("Invalid or unchanged ROM value provided.");
                        }

                        if (x.getAdditionalFeatures() != null && !x.getAdditionalFeatures().equals(oldSubProduct.getAdditionalFeatures())) {
                            oldSubProduct.setAdditionalFeatures(x.getAdditionalFeatures());
                        } else {
                            log.error("Invalid or unchanged additional features provided.");
                            throw new BadRequestException("Invalid or unchanged additional features provided.");
                        }

                        if (x.getPrice() != null && !x.getPrice().equals(oldSubProduct.getPrice())) {
                            oldSubProduct.setPrice(x.getPrice());
                        } else {
                            log.error("Invalid or unchanged price value provided.");
                            throw new BadRequestException("Invalid or unchanged price value provided.");
                        }

                        if (x.getQuantity() != 0 && oldSubProduct.getQuantity() != x.getQuantity()) {
                            oldSubProduct.setQuantity(x.getQuantity());
                        } else {
                            log.error("Invalid or unchanged quantity value provided.");
                            throw new BadRequestException("Invalid or unchanged quantity value provided.");
                        }

                        if (x.getCodeColor() != null && !x.getCodeColor().equals(oldSubProduct.getCodeColor())) {
                            oldSubProduct.setCodeColor(x.getCodeColor());
                        } else {
                            log.error("Invalid or unchanged code color value provided.");
                            throw new BadRequestException("Invalid or unchanged code color value provided.");
                        }

                        if (x.getImages() != null && !x.getImages().equals(oldSubProduct.getImages())) {
                            oldSubProduct.setImages(x.getImages());
                        } else {
                            log.error("Invalid or unchanged images provided.");
                            throw new BadRequestException("Invalid or unchanged images provided.");
                        }
                    });
                }
            }
            case "Smartphone" -> {
                if (Objects.equals(oldSubProduct.getId(), subProductId)) {
                    productRequest.getSubProductRequests().forEach(x -> {

                        if (x.getSim() != 0 && oldSubProduct.getPhone().getSim() != x.getSim()) {
                            oldSubProduct.getPhone().setSim(x.getSim());
                        } else {
                            log.error("Invalid or unchanged SIM value provided.");
                            throw new BadRequestException("Invalid or unchanged SIM value provided.");
                        }

                        if (x.getScreenSize() != 0 && oldSubProduct.getPhone().getScreenSize() != x.getScreenSize()) {
                            oldSubProduct.getPhone().setScreenSize(x.getScreenSize());
                        } else {
                            log.error("Invalid or unchanged screen size value provided.");
                            throw new BadRequestException("Invalid or unchanged screen size value provided.");
                        }

                        if (x.getBatteryCapacity() != null && !x.getBatteryCapacity().equals(oldSubProduct.getPhone().getBatteryCapacity())) {
                            oldSubProduct.getPhone().setBatteryCapacity(x.getBatteryCapacity());
                        } else {
                            log.error("Invalid or unchanged battery capacity value provided.");
                            throw new BadRequestException("Invalid or unchanged battery capacity value provided.");
                        }

                        if (x.getDiagonalScreen() != null && !x.getDiagonalScreen().equals(oldSubProduct.getPhone().getDiagonalScreen())) {
                            oldSubProduct.getPhone().setDiagonalScreen(x.getDiagonalScreen());
                        } else {
                            log.error("Invalid or unchanged diagonal screen value provided.");
                            throw new BadRequestException("Invalid or unchanged diagonal screen value provided.");
                        }

                        if (x.getRam() != 0 && oldSubProduct.getRam() != x.getRam()) {
                            oldSubProduct.setRam(x.getRam());
                        } else {
                            log.error("Invalid or unchanged RAM value provided.");
                            throw new BadRequestException("Invalid or unchanged RAM value provided.");
                        }

                        if (x.getScreenResolution() != null && !x.getScreenResolution().equals(oldSubProduct.getScreenResolution())) {
                            oldSubProduct.setScreenResolution(x.getScreenResolution());
                        } else {
                            log.error("Invalid or unchanged screen resolution value provided.");
                            throw new BadRequestException("Invalid or unchanged screen resolution value provided.");
                        }

                        if (x.getRom() != 0 && oldSubProduct.getRom() != x.getRom()) {
                            oldSubProduct.setRom(x.getRom());
                        } else {
                            log.error("Invalid or unchanged ROM value provided.");
                            throw new BadRequestException("Invalid or unchanged ROM value provided.");
                        }

                        if (x.getAdditionalFeatures() != null && !x.getAdditionalFeatures().equals(oldSubProduct.getAdditionalFeatures())) {
                            oldSubProduct.setAdditionalFeatures(x.getAdditionalFeatures());
                        } else {
                            log.error("Invalid or unchanged additional features provided.");
                            throw new BadRequestException("Invalid or unchanged additional features provided.");
                        }

                        if (x.getPrice() != null && !x.getPrice().equals(oldSubProduct.getPrice())) {
                            oldSubProduct.setPrice(x.getPrice());
                        } else {
                            log.error("Invalid or unchanged price value provided.");
                            throw new BadRequestException("Invalid or unchanged price value provided.");
                        }

                        if (x.getQuantity() != 0 && oldSubProduct.getQuantity() != x.getQuantity()) {
                            oldSubProduct.setQuantity(x.getQuantity());
                        } else {
                            log.error("Invalid or unchanged quantity value provided.");
                            throw new BadRequestException("Invalid or unchanged quantity value provided.");
                        }

                        if (x.getCodeColor() != null && !x.getCodeColor().equals(oldSubProduct.getCodeColor())) {
                            oldSubProduct.setCodeColor(x.getCodeColor());
                        } else {
                            log.error("Invalid or unchanged code color value provided.");
                            throw new BadRequestException("Invalid or unchanged code color value provided.");
                        }

                        if (x.getImages() != null && !x.getImages().equals(oldSubProduct.getImages())) {
                            oldSubProduct.setImages(x.getImages());
                        } else {
                            log.error("Invalid or unchanged images provided.");
                            throw new BadRequestException("Invalid or unchanged images provided.");
                        }
                    });
                }
            }
        }

        log.info("Product update successfully");
        subProductRepository.save(oldSubProduct);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

}

