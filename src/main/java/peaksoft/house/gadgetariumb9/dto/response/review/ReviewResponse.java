package peaksoft.house.gadgetariumb9.dto.response.review;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class ReviewResponse {

  private Long reviewId;

  private String userFullName;

  private String userAvatar;

  private int grade;

  private String comment;

  private String answer;

  private String dateOfCreatAd;

  private String imageLink;

  private boolean isMy;

  public ReviewResponse(Long reviewId, String userFullName, String userAvatar,
      int grade, String comment, String answer, String dateOfCreatAd, String imageLink) {
    this.reviewId = reviewId;
    this.userFullName = userFullName;
    this.userAvatar = userAvatar;
    this.grade = grade;
    this.comment = comment;
    this.answer = answer;
    this.dateOfCreatAd = dateOfCreatAd;
    this.imageLink = imageLink;
  }
}
