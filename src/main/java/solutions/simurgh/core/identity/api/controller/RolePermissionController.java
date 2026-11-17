package solutions.simurgh.core.identity.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import solutions.simurgh.core.identity.api.dto.response.PermissionResponse;
import solutions.simurgh.core.identity.api.mapper.PermissionMapper;
import solutions.simurgh.core.identity.application.service.RolePermissionService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/roles/{roleId}/permissions")
public class RolePermissionController {

  private final RolePermissionService rolePermissionService;
  private final PermissionMapper permissionMapper;

  public RolePermissionController(
          RolePermissionService rolePermissionService,
          PermissionMapper permissionMapper
  ) {
    this.rolePermissionService = rolePermissionService;
    this.permissionMapper = permissionMapper;
  }

  @PostMapping("/{permissionId}")
  public ResponseEntity<Void> assignPermission(
          @PathVariable UUID roleId,
          @PathVariable UUID permissionId
  ) {
    rolePermissionService.assignPermission(roleId, permissionId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{permissionId}")
  public ResponseEntity<Void> removePermission(
          @PathVariable UUID roleId,
          @PathVariable UUID permissionId
  ) {
    rolePermissionService.removePermission(roleId, permissionId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Set<PermissionResponse>> getRolePermissions(
          @PathVariable UUID roleId
  ) {
    Set<PermissionResponse> permissions = rolePermissionService
            .getRolePermissions(roleId)
            .stream()
            .map(permissionMapper::toResponse)
            .collect(Collectors.toSet());
    return ResponseEntity.ok(permissions);
  }
}