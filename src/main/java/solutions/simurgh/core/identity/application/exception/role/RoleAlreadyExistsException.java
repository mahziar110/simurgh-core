package solutions.simurgh.core.identity.application.exception.role;

public class RoleAlreadyExistsException extends RuntimeException {
  public RoleAlreadyExistsException(String code) { super("Role already exists: " + code); }
}
