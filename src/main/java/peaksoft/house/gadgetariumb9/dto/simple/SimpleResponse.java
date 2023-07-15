package peaksoft.house.gadgetariumb9.dto.simple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class SimpleResponse {

    private String message;

    private HttpStatus httpStatus;
}
