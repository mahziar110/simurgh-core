package solutions.simurgh.core.identity.application.exception.permission;

public class PermissionAlreadyExistsException extends RuntimeException {
  public PermissionAlreadyExistsException(String code) { super("Permission already exists: " + code); }
}
