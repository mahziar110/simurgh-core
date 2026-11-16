package solutions.simurgh.core.identity.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solutions.simurgh.core.identity.application.exception.role.RoleAlreadyExistsException;
import solutions.simurgh.core.identity.application.exception.role.RoleNotFoundException;
import solutions.simurgh.core.identity.application.exception.role.SystemRoleModificationException;
import solutions.simurgh.core.identity.domain.model.Role;
import solutions.simurgh.core.identity.domain.repository.RoleRepository;

import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class RoleService {
  private static final Logger logger =
          LoggerFactory.getLogger(RoleService.class);
  private final RoleRepository repository;

  @Autowired
  public RoleService(RoleRepository repository) {
    this.repository = repository;
  }

  public Role createRole(
          String name,
          String code,
          String description
  ) {
    logger.debug(
            "Creating Role, role={}",
            code
    );
    Role role = new Role(name, code, description);
    if (repository.existsByCode(role.getCode())) {
      logger.warn(
              "Role creation rejected. Role already exists. role={}",
              role.getCode()
      );
      throw new RoleAlreadyExistsException(role.getCode());
    }
    if (repository.existsByName(role.getName())) {
      logger.warn(
              "Role creation rejected. Role already exists. role={}",
              role.getName()
      );
      throw new RoleAlreadyExistsException(role.getName());
    }
    Role savedRole = repository.save(role);
    logger.info(
            "User created successfully. userId={}",
            savedRole.getId()
    );
    return savedRole;
  }

  @Transactional
  public Role getRoleById(UUID id) {
    return findRoleById(id);
  }

  @Transactional(readOnly = true)
  public Role getRoleByCode(String code) {
    String finalCode = code.trim().toUpperCase(Locale.ROOT);
    return repository.findByCode(code)
            .orElseThrow(() ->
                    new RoleNotFoundException(finalCode)
            );
  }

  @Transactional(readOnly = true)
  public Page<Role> getAllRoles(Pageable pageable) {
    return repository.findAll(pageable);
  }


  public Role updateRole(
          UUID id,
          String name,
          String code,
          String description
  ) {
    Role role = findRoleById(id);
    if (role.isSystem())
      throw new SystemRoleModificationException(role.getCode());
    name = name.trim();
    code = code.trim().toUpperCase(Locale.ROOT);
    if (repository.existsByNameAndIdNot(name, id)) {
      throw new RoleAlreadyExistsException(name);
    }
    if (repository.existsByCodeAndIdNot(code, id)) {
      throw new RoleAlreadyExistsException(code);
    }
    role.setName(name);
    role.setCode(code);
    role.setDescription(description);
    Role updatedRole = repository.save(role);
    logger.info(
            "Role updated successfully. Role ID: {}, Code: {}",
            updatedRole.getId(),
            updatedRole.getCode()
    );
    return updatedRole;
  }

  public void deleteRole(UUID id) {
    Role role = findRoleById(id);
    if (role.isSystem())
      throw new SystemRoleModificationException(role.getCode());
    repository.delete(role);
    logger.info(
            "Role deleted successfully. Role ID: {}, Code: {}",
            role.getId(),
            role.getCode()
    );
  }

  private Role findRoleById(UUID id) {
    return repository.findById(id)
            .orElseThrow(() ->
                    new RoleNotFoundException(id)
            );
  }
}
