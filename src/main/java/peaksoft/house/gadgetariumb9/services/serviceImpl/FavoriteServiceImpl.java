package peaksoft.house.gadgetariumb9.services.serviceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.response.subProductResponse.SubProductResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.models.SubProduct;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.repositories.SubProductRepository;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import peaksoft.house.gadgetariumb9.services.FavoriteService;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final UserRepository userRepository;

    private final SubProductRepository subProductRepository;

    private final JdbcTemplate jdbcTemplate;

    private final JwtService jwtService;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("successfully works the get authenticate user method");
        return userRepository.findByEmail(login).orElseThrow(() -> {
            log.error("User not found");
            return new NotFoundException("User not found!");
        });
    }

    @Override
    public SimpleResponse addAndDeleteFavorite(Long subProductId) {
        User user = getAuthenticateUser();
        List<Long> favorites = user.getFavorite();
        boolean hasChanges = false;

        SubProduct subProduct = subProductRepository.findById(subProductId).orElseThrow(() -> {
            log.error("SubProduct with id: " + subProductId + " is not found");
            return new NotFoundException("SubProduct with id: " + subProductId + " is not found");
        });

        if (favorites.contains(subProductId)) {
            favorites.remove(subProductId);
            log.info("Successfully removed the product with id " + subProductId + " from favorites");
            hasChanges = true;
        } else {
            favorites.add(subProductId);
            log.info("Successfully added the product with id " + subProductId + " to favorites");
            hasChanges = true;
        }

        if (hasChanges) {
            user.setFavorite(favorites);
            userRepository.save(user);
        }

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully added or removed from favorites.")
                .build();
    }


    @Override
    public SimpleResponse clearFavorite() {
        User user = getAuthenticateUser();
        if (user == null) {
            log.error("User not authenticated.");
            throw new NotFoundException("User not authenticated.");
        }

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
        User user = jwtService.getAuthentication();
        if (user == null) {
            log.error("User not authenticated.");
            throw new NotFoundException("User not authenticated.");
        } else {
            log.error(user.getId().toString());
            String query = """
                  SELECT sp.id,
                         sp.article_number,
                         sp.price,
                         sp.quantity,
                         sp.ram,
                         sp.rom,
                         sp.additional_features,
                         sp.code_color,
                         sp.screen_resolution,
                         COALESCE(
                         (SELECT spi.images
                          FROM sub_product_images spi
                          WHERE spi.sub_product_id = sp.id
                          LIMIT 1),' ') AS image
                          
                  FROM sub_products sp
                           JOIN sub_product_images spi ON sp.id = spi.sub_product_id
                           JOIN user_favorite uf ON uf.favorite = sp.id
                           JOIN users u ON uf.user_id = u.id
                  WHERE u.id = ?
                       """;
           return jdbcTemplate.query(
                    query,
                    (rs, rowNum) -> new SubProductResponse(
                            rs.getLong("id"),
                            rs.getInt("ram"),
                            rs.getString("screen_resolution"),
                            rs.getInt("rom"),
                            rs.getString("additional_features"),
                            rs.getBigDecimal("price"),
                            rs.getInt("quantity"),
                            rs.getString("code_color"),
                            rs.getLong("article_number"),
                            rs.getString("image")),
                    user.getId());

        }
    }


    @Override
    public SimpleResponse deleteFavorite(List<Long> subProductIds) {
        User user = getAuthenticateUser();
        if (user == null) {
            log.error("User not authenticated.");
            throw new NotFoundException("User not authenticated.");
        }

        for (Long subProductId : subProductIds) {
            SubProduct subProduct = subProductRepository.findById(subProductId).orElseThrow(() -> {
                log.error("SubProduct with id: " + subProductId + " is not found");
                return new NotFoundException("SubProduct with id: " + subProductId + " is not found");
            });

            List<Long> favorites = user.getFavorite();
            if (favorites.contains(subProductId)) {
                favorites.remove(subProductId);
                log.info("Successfully removed the product with id " + subProductId + " from favorites.");
            }
        }

        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully removed favorites.")
                .build();
    }

}
