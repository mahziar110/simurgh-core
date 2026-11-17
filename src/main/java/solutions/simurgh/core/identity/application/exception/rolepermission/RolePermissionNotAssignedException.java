package solutions.simurgh.core.identity.application.exception.rolepermission;

import java.util.UUID;

public class RolePermissionNotAssignedException
        extends RuntimeException {
  public RolePermissionNotAssignedException(
          UUID roleId,
          UUID permissionId
  ) {
    super(
            "Permission '" + permissionId +
                    "' is not assigned to role '" + roleId + "'."
    );
  }
}