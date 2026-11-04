package solutions.simurgh.core.identity.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solutions.simurgh.core.common.api.dto.OperationResult;
import solutions.simurgh.core.identity.api.dto.request.CreateUserRequest;
import solutions.simurgh.core.identity.api.dto.response.UserResponse;
import solutions.simurgh.core.identity.api.mapper.UserMapper;
import solutions.simurgh.core.identity.application.service.UserService;
import solutions.simurgh.core.identity.domain.model.User;

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
          @Valid @RequestBody CreateUserRequest request
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
}
