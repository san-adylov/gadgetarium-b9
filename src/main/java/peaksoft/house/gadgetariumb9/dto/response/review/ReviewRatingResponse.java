package peaksoft.house.gadgetariumb9.dto.response.review;

import lombok.*;
import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRatingResponse {

    private  int quantity;

    private double rating;

    Map<Integer, Integer> counts = new HashMap<>();

}
