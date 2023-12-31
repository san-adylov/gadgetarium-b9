package peaksoft.house.gadgetariumb9.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.response.subProduct.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.response.user.UserFavoritesResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.SubProductRepository;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import peaksoft.house.gadgetariumb9.services.FavoriteService;
import peaksoft.house.gadgetariumb9.template.FavoriteTemplate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final UserRepository userRepository;

    private final SubProductRepository subProductRepository;

    private final FavoriteTemplate favoriteTemplate;

    private final JwtService jwtService;


    @Override
    public SimpleResponse addAndDeleteFavorite(Long subProductId) {
        User user = jwtService.getAuthenticationUser();
        List<Long> favorites = user.getFavorite();

        subProductRepository.findById(subProductId).orElseThrow(() -> {
            log.error("SubProduct with id: " + subProductId + " is not found");
            return new NotFoundException("SubProduct with id: " + subProductId + " is not found");
        });

        if (favorites.contains(subProductId)) {
            favorites.remove(subProductId);
            log.info("Successfully removed the product with id " + subProductId + " from favorites");
        } else {
            favorites.add(subProductId);
            log.info("Successfully added the product with id " + subProductId + " to favorites");
        }

        user.setFavorite(favorites);
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully added or removed from favorites.")
                .build();
    }

    @Override
    public SimpleResponse addSubProductsToFavorite(List<Long> subProductIds) {
        User user = jwtService.getAuthenticationUser();
        List<Long> favorites = user.getFavorite();
        subProductIds.forEach(subProductId -> {
            subProductRepository.findById(subProductId).orElseThrow(() -> {
                log.error("SubProduct with id: " + subProductId + " is not found");
                return new NotFoundException("SubProduct with id: " + subProductId + " is not found");
            });

            if (favorites.contains(subProductId)) {
                favorites.remove(subProductId);
                log.info("Successfully removed the product with id " + subProductId + " from favorites");
            } else {
                favorites.add(subProductId);
                log.info("Successfully added the product with id " + subProductId + " to favorites");
            }
        });
        user.setFavorite(favorites);
        userRepository.save(user);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully added or removed from favorites.")
                .build();
    }


    @Override
    public SimpleResponse clearFavorite() {
        User user = jwtService.getAuthenticationUser();
        List<Long> favorites = user.getFavorite();
        favorites.clear();
        userRepository.save(user);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully cleared favorites.")
                .build();
    }

    @Override
    public List<SubProductResponse> getAllFavorite() {
        return favoriteTemplate.getAllFavorite();
    }

    @Override
    public List<UserFavoritesResponse> getAllFavoriteByUserId(Long userId) {
        return favoriteTemplate.getAllFavoriteByUserId(userId);
    }
}
