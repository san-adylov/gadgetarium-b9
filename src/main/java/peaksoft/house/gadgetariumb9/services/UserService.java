package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.request.user.UserUpdateRequest;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderHistoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.user.UserFavoritesResponse;
import peaksoft.house.gadgetariumb9.dto.response.user.UserResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import java.util.List;

public interface UserService {

    SimpleResponse userUpdate(UserUpdateRequest userUpdateRequest);

    UserResponse getUser();

    List<UserFavoritesResponse> getFavoritesByUser();

    List<OrderHistoryResponse> getOrdersByUser();



}
