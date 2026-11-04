package solutions.simurgh.core.identity.domain.model;

import jakarta.persistence.*;
import solutions.simurgh.core.identity.domain.enums.UserStatus;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
  protected User() {}

  public User(String username, String email, String firstname, String lastname) {
    setUsername(username);
    setEmail(email);
    changeName(firstname, lastname);
    this.status = UserStatus.PENDING;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(unique = true, nullable = false, length = 100)
  private String username;

  @Column(unique = true)
  private String email;

  @Column(name = "first_name", length = 100)
  private String firstname;

  @Column(name = "last_name", length = 100)
  private String lastname;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private UserStatus status;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @PrePersist
  protected void onCreate() {
    Instant now = Instant.now();
    createdAt = now;
    updatedAt = now;
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }

  public void activate() {
    this.status = UserStatus.ACTIVE;
  }

  public void disable() {
    this.status = UserStatus.DISABLED;
  }

  public void lock() {
    if (this.status != UserStatus.ACTIVE) {
      throw new IllegalStateException(
              "Only active users can be locked."
      );
    }
    this.status = UserStatus.LOCKED;
  }

  public void unlock() {
    if (this.status != UserStatus.LOCKED) {
      throw new IllegalStateException(
              "Only locked users can be unlocked."
      );
    }
    this.status = UserStatus.ACTIVE;
  }

  public void setUsername(String username) {
    if (username == null || username.isBlank()) {
      throw new IllegalArgumentException(
              "Username can not be empty."
      );
    }
    this.username = username.trim().toLowerCase(Locale.ROOT);
  }

  public void setEmail(String email) {
    this.email = email == null || email.isBlank()
            ? null
            : email.trim().toLowerCase(Locale.ROOT);
  }

  public void changeName(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public UserStatus getStatus() {
    return status;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }
}
