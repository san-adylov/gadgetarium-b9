package peaksoft.house.gadgetariumb9.dto.response.simple;

import lombok.Builder;
import org.apache.http.HttpStatus;

@Builder
public record SimpleResponse (

    String message,

    HttpStatus status
){}
