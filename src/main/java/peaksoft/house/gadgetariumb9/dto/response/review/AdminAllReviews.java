package peaksoft.house.gadgetariumb9.dto.response.review;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class AdminAllReviews {

  private Long reviewId;

  private String productImage;

  private String productName;

  private String brandName;

  private int articleNumber;

  private String userFullName;

  private String userEmail;

  private String userAvatar;

  private int grade;

  private String comment;

  private String answer;

  private String dateOfCreatAd;

  private String imageLink;

  private boolean isViewed;
}