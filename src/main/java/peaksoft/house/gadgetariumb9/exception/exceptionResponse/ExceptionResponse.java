package peaksoft.house.gadgetariumb9.exception.exceptionResponse;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ExceptionResponse(
    String message,
    HttpStatus status,
    String className
) {}