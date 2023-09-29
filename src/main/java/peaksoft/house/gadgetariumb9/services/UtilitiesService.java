package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.models.SubProduct;

import java.util.List;

public interface UtilitiesService {
    List<Long> getFavorites();

    List<Long> getComparison();

    SubProduct getSubProduct (Long subProductId);

    List<Long> getBasket();
}
