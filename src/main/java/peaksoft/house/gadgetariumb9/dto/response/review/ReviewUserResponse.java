package peaksoft.house.gadgetariumb9.dto.response.review;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUserResponse {

  private String email;

  private boolean isMy;

  private String message;

}
