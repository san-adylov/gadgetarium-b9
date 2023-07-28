package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.response.basket.BasketResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

import java.util.List;

public interface BasketService {

    SimpleResponse saveBasket(Long subProductId);

    List<BasketResponse> getAllByProductsFromTheBasket();

    SimpleResponse deleteProductByIds(List<Long> subProductIds);

    SimpleResponse deleteProductById(Long supProductId);
}
