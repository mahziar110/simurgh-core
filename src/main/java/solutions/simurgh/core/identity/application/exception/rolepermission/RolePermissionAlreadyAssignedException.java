package solutions.simurgh.core.identity.application.exception.rolepermission;

import java.util.UUID;

public class RolePermissionAlreadyAssignedException
        extends RuntimeException {
  public RolePermissionAlreadyAssignedException(
          UUID roleId,
          UUID permissionId
  ) {
    super(
            "Permission '" + permissionId +
                    "' is already assigned to role '" + roleId + "'."
    );
  }
}