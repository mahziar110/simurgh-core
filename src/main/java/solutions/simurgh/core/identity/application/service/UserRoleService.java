package solutions.simurgh.core.identity.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solutions.simurgh.core.identity.application.exception.role.RoleNotFoundException;
import solutions.simurgh.core.identity.application.exception.user.UserNotFoundException;
import solutions.simurgh.core.identity.application.exception.userrole.UserRoleAlreadyAssignedException;
import solutions.simurgh.core.identity.application.exception.userrole.UserRoleNotAssignedException;
import solutions.simurgh.core.identity.domain.model.Role;
import solutions.simurgh.core.identity.domain.model.User;
import solutions.simurgh.core.identity.domain.repository.RoleRepository;
import solutions.simurgh.core.identity.domain.repository.UserRepository;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserRoleService {
  private final Logger logger =
          LoggerFactory.getLogger(UserRoleService.class);

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Autowired
  public UserRoleService(
          UserRepository userRepository,
          RoleRepository roleRepository
  ) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public void assignRole (UUID userId, UUID roleId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RoleNotFoundException(roleId));
    boolean alreadyAssign = user.getRoles()
            .stream()
            .anyMatch(existingRole ->
                    existingRole.getId().equals(roleId)
            );
    if (alreadyAssign)
      throw new UserRoleAlreadyAssignedException(userId, roleId);
    user.getRoles().add(role);
    logger.info(
            "Role '{}' assigned to user '{}'",
            role.getCode(),
            user.getUsername()
    );
  }

  public void removeRole(UUID userId, UUID roleId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
    Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RoleNotFoundException(roleId));
    boolean removed = user.getRoles().removeIf(
            existingRole -> existingRole.getId().equals(roleId)
    );
    if (!removed) {
      throw new UserRoleNotAssignedException(userId, roleId);
    }
    logger.info(
            "Role '{}' removed from user '{}'.",
            role.getCode(),
            user.getUsername()
    );
  }

  @Transactional(readOnly = true)
  public Set<Role> getUserRoles(UUID id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    return Set.copyOf(user.getRoles());
  }
}
