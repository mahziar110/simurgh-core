package solutions.simurgh.core.identity.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solutions.simurgh.core.identity.api.dto.response.RoleResponse;
import solutions.simurgh.core.identity.api.mapper.RoleMapper;
import solutions.simurgh.core.identity.application.service.UserRoleService;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users/{userId}/roles")
public class UserRoleController {
  private final UserRoleService service;
  private final RoleMapper roleMapper;

  @Autowired
  public UserRoleController(
          UserRoleService service,
          RoleMapper mapper
  ) {
    this.service = service;
    this.roleMapper = mapper;
  }

  @PostMapping("/{roleId}")
  public ResponseEntity<Void> assignRole(
          @PathVariable UUID userId,
          @PathVariable UUID roleId
  ) {
    service.assignRole(userId, roleId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{roleId}")
  public ResponseEntity<Void> removeRole(
          @PathVariable UUID userId,
          @PathVariable UUID roleId
  ) {
    service.removeRole(userId, roleId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Set<RoleResponse>> getUserRoles(
          @PathVariable UUID userId
  ) {
    Set<RoleResponse> roles = service
            .getUserRoles(userId)
            .stream()
            .map(roleMapper::toResponse)
            .collect(Collectors.toSet());
    return ResponseEntity.ok(roles);
  }
}
