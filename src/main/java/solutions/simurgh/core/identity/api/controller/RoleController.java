package solutions.simurgh.core.identity.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solutions.simurgh.core.common.api.dto.OperationResult;
import solutions.simurgh.core.identity.api.dto.request.RoleRequest;
import solutions.simurgh.core.identity.api.dto.response.RoleResponse;
import solutions.simurgh.core.identity.api.mapper.RoleMapper;
import solutions.simurgh.core.identity.application.service.RoleService;
import solutions.simurgh.core.identity.domain.model.Role;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
  private final RoleService service;
  private final RoleMapper mapper;

  @Autowired
  public RoleController(
          RoleService service,
          RoleMapper mapper
  ) {
    this.service = service;
    this.mapper = mapper;
  }

  @PostMapping
  public ResponseEntity<OperationResult<RoleResponse>> createRole(
          @Valid @RequestBody RoleRequest request
  ) {
    Role role = service.createRole(
            request.name(),
            request.code(),
            request.description()
    );
    RoleResponse response = mapper.toResponse(role);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                    new OperationResult<>(
                            true,
                            "Role Added Successfully",
                            response
                    )
            );
  }

  @GetMapping("/{id}")
  public ResponseEntity<OperationResult<RoleResponse>> getRoleById(
          @PathVariable UUID id
  ) {
    Role role = service.getRoleById(id);
    RoleResponse response = mapper.toResponse(role);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "",
                    response
            )
    );
  }

  @GetMapping("/code/{code}")
  public ResponseEntity<OperationResult<RoleResponse>> getRoleByCode(
          @PathVariable String code
  ) {
    Role role = service.getRoleByCode(code);
    RoleResponse response = mapper.toResponse(role);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "",
                    response
            )
    );
  }

  @GetMapping
  public ResponseEntity<OperationResult<Page<RoleResponse>>> getAllUsers(
          Pageable pageable
  ) {
    Page<RoleResponse> roles = service.getAllRoles(pageable)
            .map(mapper::toResponse);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "",
                    roles
            )
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<OperationResult<RoleResponse>> updateUser(
          @PathVariable UUID id,
          @RequestBody RoleRequest request
  ) {
    Role role = service.updateRole(
            id,
            request.name(),
            request.code(),
            request.description()
    );
    RoleResponse response = mapper.toResponse(role);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "Role Updated Successfully",
                    response
            )
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRole(
          @PathVariable UUID id
  ) {
    service.deleteRole(id);
    return ResponseEntity.noContent().build();
  }
}
