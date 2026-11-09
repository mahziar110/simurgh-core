
package solutions.simurgh.core.identity.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solutions.simurgh.core.identity.application.exception.EmailAlreadyExistsException;
import solutions.simurgh.core.identity.application.exception.UserNotFoundException;
import solutions.simurgh.core.identity.application.exception.UsernameAlreadyExistsException;
import solutions.simurgh.core.identity.domain.model.User;
import solutions.simurgh.core.identity.domain.repository.UserRepository;

import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class UserService {

  private static final Logger logger =
          LoggerFactory.getLogger(UserService.class);
  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public User createUser(
          String email,
          String username,
          String firstname,
          String lastname
  ) {
    logger.debug(
            "Creating user. username={}",
            username
    );
    User user = new User(username, email, firstname, lastname);
    if (repository.existsByUsername(user.getUsername())) {
      logger.warn(
              "User creation rejected. Username already exists. username={}",
              username
      );
      throw new UsernameAlreadyExistsException(username);
    }
    if (repository.existsByEmail(user.getEmail())) {
      logger.warn(
              "User creation rejected. Email already exists."
      );
      throw new EmailAlreadyExistsException(email);
    }
    User savedUser = repository.save(user);
    logger.info(
            "User created successfully. userId={}",
            savedUser.getId()
    );
    return savedUser;
  }

  @Transactional(readOnly = true)
  public User getUserByUsername(String username) {
    String normalizedUsername = normalize(username);
    return repository.findByUsername(normalizedUsername)
            .orElseThrow(() ->
                    new UserNotFoundException(username)
            );
  }

  @Transactional(readOnly = true)
  public Page<User> getAllUsers(Pageable pageable) {
    return repository.findAll(pageable);
  }

  public User updateUser(
          UUID id,
          String username,
          String email,
          String firstname,
          String lastname
  ) {
    logger.debug("Updating user. userId={}", id);
    User user = this.getUserById(id);
    String normalizedUsername = normalize(username);
    String normalizedEmail = normalizeNullable(email);
    if (repository.existsByUsernameAndIdNot(
            normalizedUsername, id)) {
      logger.warn(
              "User update rejected. Username already exists. userId={}",
              id
      );
      throw new UsernameAlreadyExistsException(username);
    }
    if (normalizedEmail != null &&
            repository.existsByEmailAndIdNot(normalizedEmail, id)) {
      logger.warn(
              "User update rejected. Email already exists. userId={}",
              id
      );
      throw new EmailAlreadyExistsException(email);
    }
    user.setUsername(normalizedUsername);
    user.setEmail(normalizedEmail);
    user.changeName(firstname, lastname);
    logger.info("User updated successfully. userId={}", id);
    return user;
  }

  public void deleteUser(UUID id) {
    logger.debug("Deleting user. userId={}", id);
    User user = this.getUserById(id);
    repository.delete(user);
    logger.info("User deleted successfully. userId={}", id);
  }

  public User activateUser(UUID id) {
    logger.debug("Activating user. userId={}", id);
    User user = this.getUserById(id);
    user.activate();
    logger.info("User activated successfully. userId={}", id);
    return user;
  }

  public User lockUser(UUID id) {
    logger.debug("Locking user. userId={}", id);
    User user = this.getUserById(id);
    user.lock();
    logger.info("User locked successfully. userId={}", id);
    return user;
  }

  public User unlockUser(UUID id) {
    logger.debug("Unlocking user. userId={}", id);
    User user = this.getUserById(id);
    user.unlock();
    logger.info("User unlocked successfully. userId={}", id);
    return user;
  }

  public User disableUser(UUID id) {
    logger.debug("Disabling user. userId={}", id);
    User user = this.getUserById(id);
    user.disable();
    logger.info("User disabled successfully. userId={}", id);
    return user;
  }

  @Transactional(readOnly = true)
  public User getUserById(UUID id) {
    return repository.findById(id)
            .orElseThrow(() ->
                    new UserNotFoundException(id)
            );
  }

  private String normalize(String value) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(
              "Value cannot be empty."
      );
    }

    return value.trim().toLowerCase(Locale.ROOT);
  }

  private String normalizeNullable(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }

    return value.trim().toLowerCase(Locale.ROOT);
  }
}
