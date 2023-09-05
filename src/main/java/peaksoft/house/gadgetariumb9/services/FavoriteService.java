package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.response.user.UserFavoritesResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

import java.util.List;

public interface FavoriteService {

    SimpleResponse addAndDeleteFavorite(Long subProductId);

    SimpleResponse addSubProductsToFavorite(List<Long> subProductIds);

    SimpleResponse clearFavorite();

    List<SubProductResponse> getAllFavorite();

    List<UserFavoritesResponse> getAllFavoriteByUserId(Long userId);


}
