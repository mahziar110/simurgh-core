package solutions.simurgh.core.identity.application.exception.userrole;

import java.util.UUID;

public class UserRoleNotAssignedException extends RuntimeException {
  public UserRoleNotAssignedException(UUID userId, UUID roleId) {
    super(
            "Role '" + roleId + "' is not assigned to user '" + userId + "'."
    );
  }
}