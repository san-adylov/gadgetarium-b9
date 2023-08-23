package peaksoft.house.gadgetariumb9.dto.response.review;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewGradeInfo {

  private int five;

  private int four;

  private int three;

  private int two;

  private int one;

  private double rating;

  private int totalReviews;
}
