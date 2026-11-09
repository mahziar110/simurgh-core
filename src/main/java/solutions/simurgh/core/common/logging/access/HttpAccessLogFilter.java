package solutions.simurgh.core.common.logging.access;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import solutions.simurgh.core.common.logging.enums.RequestOutcome;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class HttpAccessLogFilter extends OncePerRequestFilter {
  private static final Logger logger =
          LoggerFactory.getLogger(HttpAccessLogFilter.class);
  private static final String REQUEST_ID_HEADER =
          "X-Request-ID";

  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain
  ) throws ServletException, IOException {
    String requestId = UUID.randomUUID().toString();
    long startTime = System.nanoTime();
    String exceptionType = null;
    response.setHeader(REQUEST_ID_HEADER, requestId);
    MDC.put("requestId", requestId);
    try {
      filterChain.doFilter(request, response);
    } catch (IOException | RuntimeException ex) {
      exceptionType = ex.getClass().getSimpleName();
      throw ex;
    } finally {
      long durationMs =
              (System.nanoTime() - startTime) / 1_000_000;
      int status = response.getStatus();
      if (exceptionType != null && status < 400) {
        status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
      }
      RequestOutcome outcome = determineOutcome(status, exceptionType);
      HttpAccessLogEntry entry = new HttpAccessLogEntry(
              Instant.now(),
              requestId,
              request.getMethod(),
              request.getRequestURI(),
              status,
              durationMs,
              outcome,
              exceptionType

      );
      writeLog(entry);
      MDC.remove("requestId");
    }
  }

  private RequestOutcome determineOutcome(
          int status,
          String exceptionType
  ) {
    if (exceptionType != null) {
      return RequestOutcome.ERROR;
    }
    return status >= 400 ? RequestOutcome.FAILURE : RequestOutcome.SUCCESS;
  }

  private void writeLog(HttpAccessLogEntry entry) {
    String message = """
            HTTP ACCESS | requestId={} method={} path={} \
            status={} durationMs={} outcome={} exceptionType={}
            """;
    if (entry.status() >= 500) {
      logger.error(
              message,
              entry.requestId(),
              entry.method(),
              entry.path(),
              entry.status(),
              entry.durationMs(),
              entry.outcome(),
              entry.exceptionType()
      );
    } else if (entry.status() >= 400) {
      logger.warn(
              message,
              entry.requestId(),
              entry.method(),
              entry.path(),
              entry.status(),
              entry.durationMs(),
              entry.outcome(),
              entry.exceptionType()
      );
    } else {

      logger.info(
              message,
              entry.requestId(),
              entry.method(),
              entry.path(),
              entry.status(),
              entry.durationMs(),
              entry.outcome(),
              entry.exceptionType()
      );
    }
  }
}
