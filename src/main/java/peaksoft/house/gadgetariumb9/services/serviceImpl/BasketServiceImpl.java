package peaksoft.house.gadgetariumb9.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.response.basket.BasketInfographicResponse;
import peaksoft.house.gadgetariumb9.dto.response.basket.BasketResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.Basket;
import peaksoft.house.gadgetariumb9.models.SubProduct;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.BasketRepository;
import peaksoft.house.gadgetariumb9.repositories.SubProductRepository;
import peaksoft.house.gadgetariumb9.services.BasketService;
import peaksoft.house.gadgetariumb9.template.BasketTemplate;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    private final JwtService jwtService;
    private final BasketTemplate basketTemplate;
    private final BasketRepository basketRepository;
    private final SubProductRepository subProductRepository;


    @Override
    public SimpleResponse saveBasket(Long subProductId) {
        User user = jwtService.getAuthentication();
        SubProduct subProduct = subProductRepository.findById(subProductId).orElseThrow(() -> {
            log.error("Sub product with id: %s not found".formatted(subProductId));
            return new NotFoundException("Sub product with id: %s not found".formatted(subProductId));
        });
        basketRepository.save(
                Basket
                        .builder()
                        .user(user)
                        .subProducts(List.of(subProduct))
                        .build());
        log.info("Basket with id: %s successfully saved".formatted(subProductId));
        return SimpleResponse
                .builder()
                .message("Basket with successfully saved")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public List<BasketResponse> getAllByProductsFromTheBasket() {
        return basketTemplate.getAllByProductsFromTheBasket();
    }

    @Override
    public SimpleResponse deleteProductByIds(List<Long> subProductIds) {
        User user = jwtService.getAuthentication();
        basketRepository.deleteAll(basketRepository.getBasketByUserId(user.getId()));
        return SimpleResponse
                .builder()
                .message("Sub product deleted")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse deleteProductById(Long supProductId) {
        List<Basket> baskets = basketRepository.getBasketBySubProductId(supProductId);
        baskets.stream()
                .findFirst()
                .ifPresent(basketRepository::delete);
        log.info(baskets.toString());
        return SimpleResponse
                .builder()
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public BasketInfographicResponse getInfographic() {
        return basketTemplate.getInfographic();
    }
}
