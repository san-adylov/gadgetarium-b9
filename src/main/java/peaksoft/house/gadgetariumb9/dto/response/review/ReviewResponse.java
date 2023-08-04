package peaksoft.house.gadgetariumb9.dto.response.review;

import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReviewResponse {

  private String userFullName;

  private String userAvatar;

  private int grade;

  private String comment;

  private String answer;

  private String dateOfCreatAd;

  private String imageLink;

  public ReviewResponse(String userFullName, String userAvatar,
      int grade, String comment, String answer, String dateOfCreatAd, String imageLink) {
    this.userFullName = userFullName;
    this.userAvatar = userAvatar;
    this.grade = grade;
    this.comment = comment;
    this.answer = answer;
    this.dateOfCreatAd = dateOfCreatAd;
    this.imageLink = imageLink;
  }
}
