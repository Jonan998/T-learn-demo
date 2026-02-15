package ru.teducation.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.teducation.dto.ErrorResponse;
import ru.teducation.exception.AuthenticationException;
import ru.teducation.exception.ConflictException;
import ru.teducation.exception.NotFoundException;
import ru.teducation.exception.TooManyRequestException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponse("validation_error", "Некорректные данные запроса"));
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ErrorResponse("unauthorized", "Требуется авторизация"));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorResponse("not_found", "Ресурс не найден"));
  }

  @ExceptionHandler(TooManyRequestException.class)
  public ResponseEntity<ErrorResponse> handleTooManyRequests(TooManyRequestException ex) {

    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
        .header("Retry-After", String.valueOf(ex.getRetryAfter()))
        .body(new ErrorResponse("too_many_requests", "Слишком много запросов. Попробуйте позже."));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAnyException(Exception ex) {
    log.error("Unexpected error", ex);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            new ErrorResponse(
                "server_error", "Произошла непредвиденная ошибка. Мы уже разбираемся."));
  }


  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
    return new ErrorResponse("bad_request", ex.getMessage());
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ErrorResponse("conflict", "Такое название уже существует"));
  }
}
