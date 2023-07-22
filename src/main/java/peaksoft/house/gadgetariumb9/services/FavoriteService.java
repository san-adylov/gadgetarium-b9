package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.response.subProductResponse.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

import java.util.List;

public interface FavoriteService {

    SimpleResponse addAndDeleteFavorite(Long subProductId);

    SimpleResponse clearFavorite();

    List<SubProductResponse> getAllFavorite();

    SimpleResponse deleteFavorite(Long subProductId);
}
