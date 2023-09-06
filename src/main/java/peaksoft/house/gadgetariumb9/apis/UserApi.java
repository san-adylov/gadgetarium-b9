package peaksoft.house.gadgetariumb9.apis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.house.gadgetariumb9.dto.request.user.UserUpdateRequest;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderHistoryResponse;
import peaksoft.house.gadgetariumb9.dto.response.order.OrderInfoByUserResponse;
import peaksoft.house.gadgetariumb9.dto.response.user.UserFavoritesResponse;
import peaksoft.house.gadgetariumb9.dto.response.user.UserResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.UserService;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "Endpoints for managing user information, favorites, and orders")
public class UserApi {

    private final UserService userService;

    @PutMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Update user information", description = "Updates the user information based on the provided request.")
    public SimpleResponse updateUser(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        return userService.userUpdate(userUpdateRequest);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get user information", description = "Retrieves detailed user information.")
    public UserResponse getUserInfo() {
        return userService.getUser();
    }

    @GetMapping("/favorites")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get user favorites", description = "Retrieves the list of user's favorite items.")
    public List<UserFavoritesResponse> getFavoritesByUserId() {
        return userService.getFavoritesByUser();
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get user order history", description = "Retrieves the order history for the user.")
    public List<OrderHistoryResponse> getOrdersByUserId() {
        return userService.getOrdersByUser();
    }

    @GetMapping("/getPhoneNumber")
    @Operation(summary = "Get User Phone Number", description = "Retrieve the user's phone number.")
    public Map<String, String> getPhoneNumber() {
        return userService.getPhoneNumber();
    }

    @GetMapping("/user_order/{order_id}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get user's order", description = "User's order information")
    public OrderInfoByUserResponse getOrderInfoByUser(@PathVariable Long order_id){
        return userService.getOrderInfoByUser(order_id);
    }
}


