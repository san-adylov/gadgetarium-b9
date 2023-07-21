package peaksoft.house.gadgetariumb9.dto.request.review;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
public class ReviewRatingRequest {

    @NotNull(message = "SubProductId cannot be null")
    private Long subProductId;
}