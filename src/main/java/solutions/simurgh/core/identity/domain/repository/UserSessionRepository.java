package solutions.simurgh.core.identity.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solutions.simurgh.core.identity.domain.model.UserSession;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
  Optional<UserSession> findByRefreshTokenHash(String refreshTokenHash);
  List<UserSession> findAllByUserIdOrderByCreatedAtDesc(UUID userId);
  Optional<UserSession> findByIdAndUserId(UUID sessionId, UUID userId);
  List<UserSession> findAllByUserIdAndRevokedAtIsNullAndExpiresAtAfterOrderByCreatedAtDesc(
          UUID userId,
          Instant now
  );
}
