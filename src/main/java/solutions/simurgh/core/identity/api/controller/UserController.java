package solutions.simurgh.core.identity.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solutions.simurgh.core.common.api.dto.OperationResult;
import solutions.simurgh.core.identity.api.dto.request.UserRequest;
import solutions.simurgh.core.identity.api.dto.response.UserResponse;
import solutions.simurgh.core.identity.api.mapper.UserMapper;
import solutions.simurgh.core.identity.application.service.UserService;
import solutions.simurgh.core.identity.domain.model.User;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService service;
  private final UserMapper mapper;

  @Autowired
  public UserController(
          UserService service,
          UserMapper mapper
  ) {
    this.service = service;
    this.mapper = mapper;
  }

  @PostMapping
  public ResponseEntity<OperationResult<UserResponse>> createUser(
          @Valid @RequestBody UserRequest request
  ) {
    User user = service.createUser(
            request.email(),
            request.username(),
            request.firstname(),
            request.lastname()
    );
    UserResponse response = mapper.toResponse(user);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                    new OperationResult<UserResponse>(
                            true,
                            "User Added Successfully.",
                            response
                    )
            );
  }

  @GetMapping("/{id}")
  public ResponseEntity<OperationResult<UserResponse>> getUser(@PathVariable UUID id) {
    UserResponse user = mapper.toResponse(service.getUserById(id));
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "",
                    user
            )
    );
  }

  @GetMapping
  public ResponseEntity<OperationResult<Page<UserResponse>>> getAllUsers(
          Pageable pagable
  ) {
    Page<UserResponse> users = service.getAllUsers(pagable)
            .map(mapper::toResponse);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "",
                    users
            )
    );
  }

  @PutMapping("/{id}")
  public ResponseEntity<OperationResult<UserResponse>> updateUser(
          @PathVariable UUID id,
          @RequestBody UserRequest request
  ) {
    User user = service.updateUser(
            id,
            request.username(),
            request.email(),
            request.firstname(),
            request.lastname()
    );
    UserResponse updatedUser = mapper.toResponse(user);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "User Updated Successfully.",
                    updatedUser
            )
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(
          @PathVariable UUID id
  ) {
    service.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/activate")
  public ResponseEntity<OperationResult<UserResponse>> activateUser(
          @PathVariable UUID id
  ) {
    User user = service.activateUser(id);
    UserResponse activatedUser = mapper.toResponse(user);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "User Activated Successfully.",
                    activatedUser
            )
    );
  }

  @PostMapping("/{id}/disable")
  public ResponseEntity<OperationResult<UserResponse>> disableUser(
          @PathVariable UUID id
  ) {
    User user = service.disableUser(id);
    UserResponse disabledUser = mapper.toResponse(user);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "User Has Been Disabled Successfully.",
                    disabledUser
            )
    );
  }

  @PostMapping("/{id}/lock")
  public ResponseEntity<OperationResult<UserResponse>> lockUser(
          @PathVariable UUID id
  ) {
    User user = service.lockUser(id);
    UserResponse lockedUser = mapper.toResponse(user);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "User Has Been Locked Successfully.",
                    lockedUser
            )
    );
  }

  @PostMapping("/{id}/unlock")
  public ResponseEntity<OperationResult<UserResponse>> unlockUser(
          @PathVariable UUID id
  ) {
    User user = service.unlockUser(id);
    UserResponse unlockedUser = mapper.toResponse(user);
    return ResponseEntity.ok(
            new OperationResult<>(
                    true,
                    "User Has Been Unlocked Successfully.",
                    unlockedUser
            )
    );
  }
}
