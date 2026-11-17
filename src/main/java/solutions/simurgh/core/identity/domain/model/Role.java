package solutions.simurgh.core.identity.domain.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {
  protected Role() {}

  public Role(
          String name,
          String code,
          String description
  ) {
    setName(name);
    setCode(code);
    setDescription(description);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(unique = true, length = 100)
  private String name;

  @Column(nullable = false, unique = true, length = 100)
  private String code;

  @Column(columnDefinition = "TEXT", length = 500)
  private String description;

  @Column(name = "is_system")
  private boolean isSystem = false;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }

  public void setName(String name) { this.name = name.trim(); }

  public void setCode(String code) {
    this.code = code.toUpperCase(Locale.ROOT).trim();
  }

  public void setDescription(String description) { this.description = description; }

  public UUID getId() { return id; }

  public String getName() { return name; }

  public String getCode() { return code; }

  public String getDescription() { return description; }

  public boolean isSystem() { return isSystem; }

  public Instant getCreatedAt() { return createdAt; }

  public Instant getUpdatedAt() { return updatedAt; }
}
