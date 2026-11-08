package solutions.simurgh.core.common.api.dto;

import solutions.simurgh.core.common.api.enums.ErrorCode;

public record ApiError (
        boolean success,
        ErrorCode code,
        String message
) {}
