package solutions.simurgh.core.identity.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import solutions.simurgh.core.identity.domain.model.Permission;
import solutions.simurgh.core.identity.domain.repository.PermissionRepository;
import solutions.simurgh.core.identity.application.exception.permission.PermissionAlreadyExistsException;
import solutions.simurgh.core.identity.application.exception.permission.PermissionNotFoundException;
import solutions.simurgh.core.identity.application.exception.permission.SystemPermissionModificationException;

import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class PermissionService {

  private static final Logger logger =
          LoggerFactory.getLogger(PermissionService.class);

  private final PermissionRepository repository;

  public PermissionService(PermissionRepository repository) {
    this.repository = repository;
  }

  public Permission createPermission(
          String name,
          String code,
          String description
  ) {
    logger.debug(
            "Creating Permission, permission={}",
            code
    );
    name = name.trim();
    code = normalizeCode(code);
    if (repository.existsByName(name)) {
      logger.warn(
              "Permission creation rejected. Permission already exists. permission={}",
              code
      );
      throw new PermissionAlreadyExistsException(name);
    }
    if (repository.existsByCode(code)) {
      logger.warn(
              "Permission creation rejected. Permission already exists. permission={}",
              name
      );

      throw new PermissionAlreadyExistsException(code);
    }
    Permission permission = new Permission(
            name,
            code,
            description
    );
    Permission savedPermission = repository.save(permission);
    logger.info(
            "Permission created successfully. Permission ID: {}, Code: {}",
            savedPermission.getId(),
            savedPermission.getCode()
    );
    return savedPermission;
  }

  @Transactional(readOnly = true)
  public Permission getPermissionById(UUID id) {
    return findPermissionById(id);
  }

  @Transactional(readOnly = true)
  public Permission getPermissionByCode(String code) {
    String finalCode = normalizeCode(code);

    return repository.findByCode(finalCode)
            .orElseThrow(() ->
                    new PermissionNotFoundException(finalCode)
            );
  }

  @Transactional(readOnly = true)
  public Page<Permission> getAllPermissions(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public Permission updatePermission(
          UUID id,
          String name,
          String code,
          String description
  ) {
    Permission permission = findPermissionById(id);
    if (permission.isSystem()) {
      throw new SystemPermissionModificationException(permission.getCode());
    }
    name = name.trim();
    code = normalizeCode(code);
    code = code.trim().toUpperCase(Locale.ROOT);
    if (repository.existsByNameAndIdNot(name, id)) {
      throw new PermissionAlreadyExistsException(name);
    }
    if (repository.existsByCodeAndIdNot(code, id)) {
      throw new PermissionAlreadyExistsException(code);
    }
    permission.setName(name);
    permission.setCode(code);
    permission.setDescription(description);
    Permission updatedPermission = repository.save(permission);
    logger.info(
            "Permission updated successfully. Permission ID: {}, Code: {}",
            updatedPermission.getId(),
            updatedPermission.getCode()
    );
    return updatedPermission;
  }

  public void deletePermission(UUID id) {
    Permission permission = findPermissionById(id);
    if (permission.isSystem()) {
      throw new SystemPermissionModificationException(permission.getCode());
    }
    repository.delete(permission);
    logger.info(
            "Permission deleted successfully. Permission ID: {}, Code: {}",
            permission.getId(),
            permission.getCode()
    );
  }

  private Permission findPermissionById(UUID id) {
    return repository.findById(id)
            .orElseThrow(() ->
                    new PermissionNotFoundException(id)
            );
  }

  private String normalizeCode(String code) {
    return code.trim().toUpperCase(Locale.ROOT);
  }
}
