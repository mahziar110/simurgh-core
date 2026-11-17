package solutions.simurgh.core.identity.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solutions.simurgh.core.identity.domain.model.UserSession;

import java.util.UUID;

public interface UserSessionRepository extends JpaRepository<UserSession, UUID> {
}
