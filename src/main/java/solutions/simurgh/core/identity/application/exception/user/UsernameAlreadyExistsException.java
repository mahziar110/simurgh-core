package solutions.simurgh.core.identity.application.exception.user;

public class UsernameAlreadyExistsException extends RuntimeException {
  public UsernameAlreadyExistsException(String username) {
    super("Username already exists: " + username);
  }
}
