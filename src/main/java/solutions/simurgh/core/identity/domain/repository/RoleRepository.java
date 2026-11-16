package solutions.simurgh.core.identity.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solutions.simurgh.core.identity.domain.model.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
  Optional<Role> findByCode(String code);
  Optional<Role> findByName(String name);
  boolean existsByCode(String code);
  boolean existsByName(String name);
}
