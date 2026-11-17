
package solutions.simurgh.core.identity.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import solutions.simurgh.core.common.api.dto.OperationResult;
import solutions.simurgh.core.identity.api.dto.request.PermissionRequest;
import solutions.simurgh.core.identity.api.dto.response.PermissionResponse;
import solutions.simurgh.core.identity.api.mapper.PermissionMapper;
import solutions.simurgh.core.identity.application.service.PermissionService;
import solutions.simurgh.core.identity.domain.model.Permission;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {
  private final PermissionService service;
  private final PermissionMapper mapper;

  @Autowired
  public PermissionController(
          PermissionService service,
          PermissionMapper mapper
  ) {
    this.service = service;
    this.mapper = mapper;
  }

  @PostMapping
  public ResponseEntity<OperationResult<PermissionResponse>> createPermission(
          @Valid @RequestBody PermissionRequest request
  ) {
    Permission permission = service.createPermission(
            request.name(),
            request.code(),
            request.description()
    );
    PermissionResponse response = mapper.toResponse(permission);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    new OperationResult<>(
                            true,
                            "Permission Created Successfully",
                            response
                    )
            );
  }

  @GetMapping("/{id}")
  public ResponseEntity<OperationResult<PermissionResponse>> getPermissionById(
          @PathVariable UUID id
  ) {
    Permission permission = service.getPermissionById(id);
    PermissionResponse response = mapper.toResponse(permission);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "",
                    response
            )
    );
  }

  @GetMapping("/code/{code}")
  public ResponseEntity<OperationResult<PermissionResponse>> getPermissionByCode(
          @PathVariable String code
  ) {
    Permission permission = service.getPermissionByCode(code);
    PermissionResponse response = mapper.toResponse(permission);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "",
                    response
            )
    );
  }

  @GetMapping
  public ResponseEntity<OperationResult<Page<PermissionResponse>>> getAllPermissions(
          @PageableDefault(
                  size = 20,
                  sort = "createdAt",
                  direction = Sort.Direction.DESC
          ) Pageable pageable
  ) {
    Page<PermissionResponse> permissions =
            service.getAllPermissions(pageable)
                    .map(mapper::toResponse);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "",
                    permissions
            )
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<OperationResult<PermissionResponse>> updatePermission(
          @PathVariable UUID id,
          @RequestBody PermissionRequest request
  ) {
    Permission permission = service.updatePermission(
            id,
            request.name(),
            request.code(),
            request.description()
    );
    PermissionResponse response = mapper.toResponse(permission);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "Permission Updated Successfully",
                    response
            )
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePermission(
          @PathVariable UUID id
  ) {
    service.deletePermission(id);
    return ResponseEntity.noContent().build();
  }
}
