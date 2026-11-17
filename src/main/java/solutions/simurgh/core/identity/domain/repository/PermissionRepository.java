package solutions.simurgh.core.identity.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solutions.simurgh.core.identity.domain.model.Permission;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
  Optional<Permission> findByCode(String code);
  Optional<Permission> findByName(String name);
  boolean existsByCode(String code);
  boolean existsByName(String name);
  boolean existsByCodeAndIdNot(String code, UUID id);
  boolean existsByNameAndIdNot(String name, UUID id);
}