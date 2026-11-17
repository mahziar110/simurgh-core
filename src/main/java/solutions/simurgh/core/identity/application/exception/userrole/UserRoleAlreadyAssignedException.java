package solutions.simurgh.core.identity.application.exception.userrole;

import java.util.UUID;

public class UserRoleAlreadyAssignedException extends RuntimeException {
  public UserRoleAlreadyAssignedException(UUID userId, UUID roleId) {
    super(
            "Role '" + roleId + "' is already assigned to user '" + userId + "'."
    );
  }
}