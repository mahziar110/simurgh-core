package solutions.simurgh.core.identity.application.service;

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
    User user = new User(username, email, firstname, lastname);
    if (repository.existsByUsername(user.getUsername()))
      throw new UsernameAlreadyExistsException(username);
    if (repository.existsByEmail(user.getEmail()))
      throw new EmailAlreadyExistsException(email);
    return repository.save(user);
  }

  @Transactional(readOnly = true)
  public User getUserByUsername(String username) {
    String normalizedUsername = normalize(username);
    return repository.findByUsername(normalizedUsername)
            .orElseThrow(() ->
                    new IllegalArgumentException("User not found.")
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
    User user = this.getUserById(id);
    String normalizedUsername = normalize(username);
    String normalizedEmail = normalizeNullable(email);
    if (repository.existsByUsernameAndIdNot(
            normalizedUsername, id)) {
      throw new UsernameAlreadyExistsException(username);
    }
    if (normalizedEmail != null &&
            repository.existsByEmailAndIdNot(normalizedEmail, id)) {
      throw new EmailAlreadyExistsException(email);
    }
    user.setUsername(normalizedUsername);
    user.setEmail(normalizedEmail);
    user.changeName(firstname, lastname);
    return user;
  }


  public void deleteUser(UUID id) {
    User user = this.getUserById(id);
    repository.delete(user);
  }

  public User activateUser(UUID id) {
    User user = this.getUserById(id);
    user.activate();
    return user;
  }

  public User lockUser(UUID id) {
    User user = this.getUserById(id);
    user.lock();
    return user;
  }

  public User unlockUser(UUID id) {
    User user = this.getUserById(id);
    user.unlock();
    return user;
  }

  public User disableUser(UUID id) {
    User user = this.getUserById(id);
    user.disable();
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
