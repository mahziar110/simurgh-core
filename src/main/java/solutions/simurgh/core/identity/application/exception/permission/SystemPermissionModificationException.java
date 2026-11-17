package solutions.simurgh.core.identity.application.exception.permission;

public class SystemPermissionModificationException extends RuntimeException {
  public SystemPermissionModificationException(String code) { super(code + "is a system permission, System permissions cannot be modified or deleted"); }
}
