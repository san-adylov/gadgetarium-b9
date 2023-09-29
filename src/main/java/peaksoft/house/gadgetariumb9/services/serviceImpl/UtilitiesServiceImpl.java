package peaksoft.house.gadgetariumb9.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.SubProduct;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.SubProductRepository;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import peaksoft.house.gadgetariumb9.services.UtilitiesService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UtilitiesServiceImpl implements UtilitiesService {

    private final UserRepository userRepository;

    private final JdbcTemplate jdbcTemplate;

    private final SubProductRepository subProductRepository;

    private String email() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public List<Long> getFavorites() {
        List<Long> favorites = Collections.emptyList();
        if (!email().equalsIgnoreCase("anonymousUser")) {
            User user = userRepository.getUserByEmail(email())
                    .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email())));
            favorites = jdbcTemplate.queryForList(
                    "SELECT uf.favorite FROM user_favorite uf WHERE uf.user_id = ?",
                    Long.class,
                    user.getId());
        }
        return favorites;
    }

    @Override
    public List<Long> getComparison() {
        List<Long> comparisons = Collections.emptyList();
        if (!email().equalsIgnoreCase("anonymousUser")) {
            User user = userRepository.getUserByEmail(email())
                    .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email())));
            comparisons = jdbcTemplate.queryForList(
                    "SELECT uc.comparison FROM user_comparison uc WHERE uc.user_id = ?",
                    Long.class,
                    user.getId());
        }
        return comparisons;
    }

    @Override
    public SubProduct getSubProduct(Long subProductId) {
        return subProductRepository.findById(subProductId)
                .orElseThrow(() -> {
                    log.error("SubProduct with id: " + subProductId + " is not found");
                    return new NotFoundException("SubProduct with id: " + subProductId + " is not found");
                });
    }

    @Override
    public List<Long> getBasket() {
        User user;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!email.equalsIgnoreCase("anonymousUser")) {
            user = userRepository.getUserByEmail(email)
                    .orElseThrow(() -> {
                        log.error("User with email: " + email + " is not found");
                        return new NotFoundException("User with email: " + email + " is not found");
                    });
        } else {
            user = null;
        }
        if (user != null) {
            String subProductIdsInBasketSql = "SELECT sub_products_id FROM baskets_sub_products JOIN baskets b on b.id = baskets_sub_products.baskets_id WHERE b.user_id = ?";
           return  jdbcTemplate.queryForList(subProductIdsInBasketSql, Long.class, user.getId());
        }
     return new ArrayList<>();
    }
}
