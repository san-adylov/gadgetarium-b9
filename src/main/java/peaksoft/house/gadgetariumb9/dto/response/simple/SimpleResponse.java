package peaksoft.house.gadgetariumb9.dto.response.simple;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@NoArgsConstructor
public class SimpleResponse {

  private String message;

  private HttpStatus status;

  @Builder
  public SimpleResponse(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
  }
}
