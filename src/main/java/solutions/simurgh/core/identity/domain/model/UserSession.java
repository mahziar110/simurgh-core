package solutions.simurgh.core.identity.domain.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "user_session",
        indexes = {
                @Index(name = "idx_user_session_user_id", columnList = "user_id"),
                @Index(name = "idx_user_session_refresh_token", columnList = "refresh_token_hash")
        }
)
public class UserSession {
  protected UserSession() {}

  public UserSession(
          User user,
          String refreshTokenHash,
          Instant expiresAt
  ) {
    setUser(user);
    setRefreshTokenHash(refreshTokenHash);
    setExpiresAt(expiresAt);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "refresh_token_hash", nullable = false, unique = true)
  private String refreshTokenHash;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
  }

  @Column(name = "expires_at", nullable = false)
  private Instant expiresAt;

  @Column(name = "last_used_at")
  private Instant lastUsedAt;

  @Column(name = "revoked_at")
  private Instant revokedAt;

  @Column(name = "user_agent", length = 512)
  private String userAgent;

  @Column(name = "ip_address", length = 45)
  private String ipAddress;

  public UUID getId() { return id; }

  public User getUser() { return user; }

  public String getRefreshTokenHash() { return refreshTokenHash; }

  public Instant getCreatedAt() { return createdAt; }

  public Instant getExpiresAt() { return expiresAt; }

  public Instant getLastUsedAt() { return lastUsedAt; }

  public Instant getRevokedAt() { return revokedAt; }

  public String getUserAgent() { return userAgent; }

  public String getIpAddress() { return ipAddress; }

  public boolean isRevoked() {
    return revokedAt != null;
  }

  public boolean isExpired() {
    return !expiresAt.isAfter(Instant.now());
  }

  public boolean isActive() {
    return !isRevoked() && !isExpired();
  }

  public void setUser(User user) { this.user = user; }

  public void revoke() {
    if (this.revokedAt == null)
      this.revokedAt = Instant.now();
  }

  public void setRefreshTokenHash(String refreshTokenHash) {
    this.refreshTokenHash = refreshTokenHash;
  }

  public void setExpiresAt(Instant expiresAt) {
    this.expiresAt = expiresAt;
  }

  public void setLastUsedAt(Instant lastUsedAt) {
    this.lastUsedAt = lastUsedAt;
  }

  public void setRevokedAt(Instant revokedAt) {
    this.revokedAt = revokedAt;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
}
