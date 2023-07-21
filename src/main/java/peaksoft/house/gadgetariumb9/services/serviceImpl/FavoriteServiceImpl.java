package peaksoft.house.gadgetariumb9.services.serviceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.dto.response.brand.subProductResponse.SubProductResponse;
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
        SubProduct subProduct = subProductRepository.findById(subProductId).orElseThrow(() -> {
            log.error("SubProduct with id: " + subProductId + " is not found");
            return new NotFoundException("SubProduct with id: " + subProductId + " is not found");
        });
        List<Long> favorites = user.getFavorite();

        if (favorites == null) {
            favorites = new ArrayList<>();
        }

        if (favorites.contains(subProduct.getId())) {
            favorites.remove(subProduct.getId());
            log.info("Successfully removed this product from favorites");
        } else {
            favorites.add(subProduct.getId());
            log.info("Successfully added this product to favorites");
        }

        user.setFavorite(favorites);
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully added or removed from favorites.")
                .build();
    }


    @Override
    public SimpleResponse clearFavorite(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User with id: " + userId + " is not found");
            return new NotFoundException("User with id: " + userId + " is not found");
        });

        List<Long> favorites = user.getFavorite();
        favorites.clear();

        user.setFavorite(favorites);
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully cleared favorites.")
                .build();
    }

    @Override
    public List<SubProductResponse> getAllFavorite(Long userId) {
        String query = "SELECT s.id, s.article_number, s.price, s.quantity, s.ram, s.rom, s.additional_features, s.code_color, s.screen_resolution, spi.images " +
                "FROM user_favorite AS u " +
                "JOIN sub_products AS s ON u.user_id = s.id " +
                "JOIN sub_product_images AS spi ON s.id = spi.sub_product_id " +
                "WHERE u.user_id =?";

        Map<Long, SubProductResponse> subProductMap = new HashMap<>();

        jdbcTemplate.query(
                query,
                new Object[]{userId},
                (rs, rowNum) -> {
                    long subProductId = rs.getLong("id");
                    SubProductResponse subProductResponse = subProductMap.getOrDefault(subProductId, new SubProductResponse());
                    subProductResponse.setId(subProductId);
                    subProductResponse.setArticleNumber(rs.getInt("article_number"));
                    subProductResponse.setPrice(rs.getBigDecimal("price"));
                    subProductResponse.setQuantity(rs.getInt("quantity"));
                    subProductResponse.setRam(rs.getInt("ram"));
                    subProductResponse.setRom(rs.getInt("rom"));
                    subProductResponse.setAdditionalFeatures(rs.getString("additional_features"));
                    subProductResponse.setCodeColor(rs.getString("code_color"));
                    subProductResponse.setScreenResolution(rs.getString("screen_resolution"));

                    List<String> images = subProductResponse.getImages();
                    if (images == null) {
                        images = new ArrayList<>();
                    }
                    images.add(rs.getString("images"));
                    subProductResponse.setImages(images);

                    subProductMap.put(subProductId, subProductResponse);

                    return subProductResponse;
                }
        );

        return new ArrayList<>(subProductMap.values());
    }


    @Override
    public SimpleResponse deleteFavorite(List<Long> userId) {
        for (Long userIds : userId) {
            User user = userRepository.findById(userIds).orElseThrow(() -> {
                log.error("User with id: " + userIds + " is not found");
                return new NotFoundException("User with id: " + userIds + " is not found");
            });
            userRepository.delete(user);
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully removed favorites.")
                .build();
    }
}
