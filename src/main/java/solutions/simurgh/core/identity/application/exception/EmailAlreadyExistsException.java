package solutions.simurgh.core.identity.application.exception;

public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException(String email) {
    super("Username already exists: " + email);
  }
}
