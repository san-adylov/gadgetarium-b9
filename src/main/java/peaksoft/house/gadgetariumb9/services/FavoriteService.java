package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.response.brand.subProductResponse.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

import java.util.List;

public interface FavoriteService {

    SimpleResponse addAndDeleteFavorite(List<Long> subProductId);

    SimpleResponse clearFavorite(Long userId);

    List<SubProductResponse> getAllFavorite(Long userId);

    SimpleResponse deleteFavorite(List<Long> userId);
}
