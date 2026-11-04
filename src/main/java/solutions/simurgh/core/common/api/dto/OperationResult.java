package solutions.simurgh.core.common.api.dto;

public record OperationResult<T> (
        boolean success,
        String message,
        T data
) {}
