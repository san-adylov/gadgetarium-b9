package peaksoft.house.gadgetariumb9.dto.request.review;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {

  private Long subProductId;

  private String comment;

  private int grade;

  private String imageLink;
}
