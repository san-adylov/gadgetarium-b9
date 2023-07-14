package peaksoft.house.gadgetariumb9.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import peaksoft.house.gadgetariumb9.exception.exceptionResponse.ExceptionResponse;

@RestControllerAdvice
public class GlobalException {

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleNotFound(RuntimeException e) {
    return ExceptionResponse
        .builder()
        .message(e.getMessage())
        .status(HttpStatus.NOT_FOUND)
        .className(e.getClass().getSimpleName())
        .build();
  }

}
