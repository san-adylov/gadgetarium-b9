package peaksoft.house.gadgetariumb9.dto.request.mailing;

import java.time.LocalDate;
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
  private String description;

  private String image;

  private LocalDate startDate;

  private LocalDate finishDate;
}