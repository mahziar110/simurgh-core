package solutions.simurgh.core.identity.api.dto.response;

import solutions.simurgh.core.identity.domain.enums.UserStatus;

import java.time.Instant;
import java.util.UUID;

public record UserResponse (
        UUID id,
        String username,
        String email,
        String firstname,
        String lastname,
        UserStatus status,
        Instant createdAt,
        Instant updatedAt
) {}
