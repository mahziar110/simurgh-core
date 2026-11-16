package solutions.simurgh.core.identity.application.exception.role;

public class SystemRoleModificationException extends RuntimeException {
  SystemRoleModificationException(String code) { super(code + "is a system role, System roles cannot be modified or deleted"); }
}
