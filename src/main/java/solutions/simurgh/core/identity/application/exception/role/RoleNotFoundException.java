package solutions.simurgh.core.identity.application.exception.role;

import java.util.UUID;

public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException(UUID id) { super("Role not found with id: " + id.toString()); }
  public RoleNotFoundException(String code) { super("Role not found with code: " + code); }
}
