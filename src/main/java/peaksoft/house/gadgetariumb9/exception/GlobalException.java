package peaksoft.house.gadgetariumb9.exception;

import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import peaksoft.house.gadgetariumb9.exception.exceptionResponse.ExceptionResponse;

@RestControllerAdvice
public class GlobalException {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
    List<String> errors = e
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();
    return ExceptionResponse
        .builder()
        .message(errors.toString())
        .status(HttpStatus.BAD_REQUEST)
        .className(e.getClass().getSimpleName())
        .build();
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleNotFound(NotFoundException e) {
    return ExceptionResponse
        .builder()
        .message(e.getMessage())
        .status(HttpStatus.NOT_FOUND)
        .className(e.getClass().getSimpleName())
        .build();
  }

  @ExceptionHandler(AlreadyExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ExceptionResponse handleAlreadyExist(AlreadyExistException e) {
    return ExceptionResponse
        .builder()
        .message(e.getMessage())
        .status(HttpStatus.NOT_FOUND)
        .className(e.getClass().getSimpleName())
        .build();
  }

  @ExceptionHandler(BadCredentialException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionResponse handleBadCredential(BadCredentialException e) {
    return ExceptionResponse
        .builder()
        .message(e.getMessage())
        .status(HttpStatus.BAD_REQUEST)
        .className(e.getClass().getSimpleName())
        .build();
  }

}
