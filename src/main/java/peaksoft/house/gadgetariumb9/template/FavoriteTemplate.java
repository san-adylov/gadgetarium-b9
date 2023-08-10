package peaksoft.house.gadgetariumb9.template;

import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.response.user.UserFavoritesResponse;

import java.util.List;

public interface FavoriteTemplate {

    List<SubProductResponse> getAllFavorite();

    List<UserFavoritesResponse> getAllFavoriteByUserId(Long userId);


}
