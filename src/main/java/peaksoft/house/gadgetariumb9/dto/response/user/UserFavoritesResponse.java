package peaksoft.house.gadgetariumb9.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFavoritesResponse {

    private Long subProductId;

    private String name;

    private double rating;

    private int countOfReview;

    private BigDecimal price;

    private String image;
}
