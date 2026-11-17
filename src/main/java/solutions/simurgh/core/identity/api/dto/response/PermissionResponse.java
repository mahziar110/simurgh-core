package solutions.simurgh.core.identity.api.dto.response;

import java.time.Instant;
import java.util.UUID;

public record PermissionResponse (
        UUID id,
        String name,
        String code,
        String description,
        boolean isSystem,
        Instant createdAt,
        Instant updatedAt
) {}
