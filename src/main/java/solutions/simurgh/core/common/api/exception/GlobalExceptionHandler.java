package solutions.simurgh.core.common.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import solutions.simurgh.core.common.api.dto.ApiError;
import solutions.simurgh.core.common.api.enums.ErrorCode;
import solutions.simurgh.core.identity.application.exception.EmailAlreadyExistsException;
import solutions.simurgh.core.identity.application.exception.UserNotFoundException;
import solutions.simurgh.core.identity.application.exception.UsernameAlreadyExistsException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ApiError> handleEmailAlreadyExists(
          EmailAlreadyExistsException ex
  ) {
    return buildError(
            HttpStatus.CONFLICT,
            ErrorCode.EMAIL_ALREADY_EXISTS,
            ex.getMessage()
    );
  }

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<ApiError> handleUsernameAlreadyExists(
          UsernameAlreadyExistsException ex
  ) {
    return buildError(
            HttpStatus.CONFLICT,
            ErrorCode.USERNAME_ALREADY_EXISTS,
            ex.getMessage()
    );
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiError> handleUserNotFound(
          UserNotFoundException ex
  ) {
    return buildError(
            HttpStatus.NOT_FOUND,
            ErrorCode.USER_NOT_FOUND,
            ex.getMessage()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationException(
          MethodArgumentNotValidException ex
  ) {
    String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error ->
                    error.getField() + ": " + error.getDefaultMessage()
            )
            .collect(Collectors.joining(", "));

    return buildError(
            HttpStatus.BAD_REQUEST,
            ErrorCode.VALIDATION_ERROR,
            message
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleUnexpectedException(
          Exception ex
  ) {
    return buildError(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorCode.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred."
    );
  }

  private ResponseEntity<ApiError> buildError(
          HttpStatus status,
          ErrorCode code,
          String message
  ) {
    ApiError apiError = new ApiError(
            false,
            code,
            message
    );
    return ResponseEntity.status(status).body(apiError);
  }
}
