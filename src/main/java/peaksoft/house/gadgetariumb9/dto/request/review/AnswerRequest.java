package peaksoft.house.gadgetariumb9.dto.request.review;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerRequest {

  private Long reviewId;

  private String replyToComment;
}
