package solutions.simurgh.core.identity.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solutions.simurgh.core.identity.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
  boolean existsByUsernameAndIdNot(String username, UUID id);
  boolean existsByEmailAndIdNot(String email, UUID id);
}
