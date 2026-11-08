package solutions.simurgh.core.common.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import solutions.simurgh.core.common.api.dto.ApiError;
import solutions.simurgh.core.common.api.enums.ErrorCode;
import solutions.simurgh.core.identity.application.exception.EmailAlreadyExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ApiError> handleEmailAlreadyExists(
          EmailAlreadyExistsException ex
  ) {
    return buildError (
            HttpStatus.CONFLICT,
            ErrorCode.EMAIL_ALREADY_EXISTS,
            ex.getMessage()
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
