package solutions.simurgh.core.identity.application.exception.permission;

import java.util.UUID;

public class PermissionNotFoundException extends RuntimeException {
  public PermissionNotFoundException(UUID id) { super("Permission not found with id: " + id.toString()); }
  public PermissionNotFoundException(String code) { super("Permission not found with code: " + code); }
}
