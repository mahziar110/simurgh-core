package solutions.simurgh.core.identity.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import solutions.simurgh.core.identity.application.exception.permission.PermissionNotFoundException;
import solutions.simurgh.core.identity.application.exception.role.RoleNotFoundException;
import solutions.simurgh.core.identity.application.exception.rolepermission.RolePermissionAlreadyAssignedException;
import solutions.simurgh.core.identity.application.exception.rolepermission.RolePermissionNotAssignedException;

import solutions.simurgh.core.identity.domain.model.Permission;
import solutions.simurgh.core.identity.domain.model.Role;

import solutions.simurgh.core.identity.domain.repository.PermissionRepository;
import solutions.simurgh.core.identity.domain.repository.RoleRepository;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class RolePermissionService {

  private final Logger logger =
          LoggerFactory.getLogger(RolePermissionService.class);

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  @Autowired
  public RolePermissionService(
          RoleRepository roleRepository,
          PermissionRepository permissionRepository
  ) {
    this.roleRepository = roleRepository;
    this.permissionRepository = permissionRepository;
  }

  public void assignPermission(UUID roleId, UUID permissionId) {
    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RoleNotFoundException(roleId));
    Permission permission = permissionRepository.findById(permissionId)
            .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    boolean alreadyAssigned = role.getPermissions()
            .stream()
            .anyMatch(existingPermission ->
                    existingPermission.getId().equals(permissionId)
            );
    if (alreadyAssigned) {
      throw new RolePermissionAlreadyAssignedException(
              roleId, permissionId
      );
    }
    role.getPermissions().add(permission);
    logger.info(
            "Permission '{}' assigned to role '{}'.",
            permission.getCode(),
            role.getCode()
    );
  }

  public void removePermission(UUID roleId, UUID permissionId) {
    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RoleNotFoundException(roleId));
    Permission permission = permissionRepository.findById(permissionId)
            .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    boolean removed = role.getPermissions().removeIf(
            existingPermission ->
                    existingPermission.getId().equals(permissionId)
    );
    if (!removed) {
      throw new RolePermissionNotAssignedException(
              roleId, permissionId
      );
    }
    logger.info(
            "Permission '{}' removed from role '{}'.",
            permission.getCode(),
            role.getCode()
    );
  }

  @Transactional(readOnly = true)
  public Set<Permission> getRolePermissions(UUID roleId) {
    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RoleNotFoundException(roleId));
    return Set.copyOf(role.getPermissions());
  }
}
