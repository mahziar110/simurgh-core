package solutions.simurgh.core.common.logging.access;

import org.slf4j.event.Level;
import solutions.simurgh.core.common.logging.enums.RequestOutcome;

import java.time.Instant;

public record HttpAccessLogEntry(
        Instant time,
        String requestId,
        String method,
        String path,
        int status,
        long durationMs,
        RequestOutcome outcome,
        String errorCode
) {}
