package peaksoft.house.gadgetariumb9.dto.simple;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponse {

    private String message;

    private HttpStatus status;
}