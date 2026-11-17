package solutions.simurgh.core.identity.domain.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
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

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "role_permissions",
          joinColumns = @JoinColumn(name = "role_id"),
          inverseJoinColumns = @JoinColumn(name = "permission_id")
  )
  private Set<Permission> permissions = new HashSet<>();

  @PreUpdate
  protected void onUpdate() {
    updatedAt = Instant.now();
  }

  public void setName(String name) { this.name = name.trim(); }

  public void setCode(String code) {
    this.code = code.toUpperCase(Locale.ROOT).trim();
  }

  public void setDescription(String description) { this.description = description; }

  public void setPermissions(Set<Permission> permissions) { this.permissions = permissions; }

  public UUID getId() { return id; }

  public String getName() { return name; }

  public String getCode() { return code; }

  public String getDescription() { return description; }

  public boolean isSystem() { return isSystem; }

  public Instant getCreatedAt() { return createdAt; }

  public Instant getUpdatedAt() { return updatedAt; }

  public Set<Permission> getPermissions() { return permissions; }
}
