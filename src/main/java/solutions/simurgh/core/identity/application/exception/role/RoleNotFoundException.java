package solutions.simurgh.core.identity.application.exception.role;

public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException(String code) { super("Role not found with code: " + code); }
}
