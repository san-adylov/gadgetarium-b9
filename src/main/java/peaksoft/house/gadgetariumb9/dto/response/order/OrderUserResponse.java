package peaksoft.house.gadgetariumb9.dto.response.order;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUserResponse {

  private HttpStatus status;

  private int orderNumber;

  private String message;
}