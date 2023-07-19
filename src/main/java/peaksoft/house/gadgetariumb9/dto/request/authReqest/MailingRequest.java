package peaksoft.house.gadgetariumb9.dto.request.authReqest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailingRequest {
  private String name;

  private String description;

  private String image;

  private String startDate;

  private String finishDate;
}