package peaksoft.house.gadgetariumb9.dto.simple;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record SimpleResponse (

    String message,

    HttpStatus status
){}
