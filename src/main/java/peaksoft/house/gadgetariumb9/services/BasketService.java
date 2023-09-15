package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.response.basket.BasketInfographicResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import java.util.List;

public interface BasketService {

    SimpleResponse saveBasket(Long subProductId);

    BasketInfographicResponse getAllByProductsFromTheBasket();

    SimpleResponse deleteProductByIds(List<Long> subProductIds);

    SimpleResponse deleteProductById(Long supProductId);

    SimpleResponse addSubProductForBasket(Long subProductId, int quantity);

}
