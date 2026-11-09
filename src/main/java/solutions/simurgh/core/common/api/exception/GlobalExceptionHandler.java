package solutions.simurgh.core.common.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import solutions.simurgh.core.common.api.dto.ApiError;
import solutions.simurgh.core.common.api.enums.ErrorCode;
import solutions.simurgh.core.common.logging.access.HttpAccessLogAttributes;
import solutions.simurgh.core.identity.application.exception.EmailAlreadyExistsException;
import solutions.simurgh.core.identity.application.exception.UserNotFoundException;
import solutions.simurgh.core.identity.application.exception.UsernameAlreadyExistsException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ApiError> handleEmailAlreadyExists(
          EmailAlreadyExistsException ex,
          HttpServletRequest request
  ) {
    sendErrorCodeToPutInLog(
            request,
            ErrorCode.EMAIL_ALREADY_EXISTS
    );
    return buildError(
            HttpStatus.CONFLICT,
            ErrorCode.EMAIL_ALREADY_EXISTS,
            ex.getMessage()
    );
  }

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<ApiError> handleUsernameAlreadyExists(
          UsernameAlreadyExistsException ex,
          HttpServletRequest request
  ) {
    sendErrorCodeToPutInLog(
            request,
            ErrorCode.USERNAME_ALREADY_EXISTS
    );
    return buildError(
            HttpStatus.CONFLICT,
            ErrorCode.USERNAME_ALREADY_EXISTS,
            ex.getMessage()
    );
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ApiError> handleUserNotFound(
          UserNotFoundException ex,
          HttpServletRequest request
  ) {
    sendErrorCodeToPutInLog(
            request,
            ErrorCode.USER_NOT_FOUND
    );
    return buildError(
            HttpStatus.NOT_FOUND,
            ErrorCode.USER_NOT_FOUND,
            ex.getMessage()
    );
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidationException(
          MethodArgumentNotValidException ex,
          HttpServletRequest request
  ) {
    String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error ->
                    error.getField() + ": " + error.getDefaultMessage()
            )
            .collect(Collectors.joining(", "));
    sendErrorCodeToPutInLog(
            request,
            ErrorCode.VALIDATION_ERROR
    );
    return buildError(
            HttpStatus.BAD_REQUEST,
            ErrorCode.VALIDATION_ERROR,
            message
    );
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleUnexpectedException(
          Exception ex,
          HttpServletRequest request
  ) {
    sendErrorCodeToPutInLog(
            request,
            ErrorCode.INTERNAL_SERVER_ERROR
    );
    return buildError(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorCode.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred."
    );
  }

  private void sendErrorCodeToPutInLog(
          HttpServletRequest request,
          ErrorCode errorCode
  ) {
    request.setAttribute(
            HttpAccessLogAttributes.ERROR_CODE,
            errorCode.toString()
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
