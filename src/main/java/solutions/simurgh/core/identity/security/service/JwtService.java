
package solutions.simurgh.core.identity.security.service;


import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import solutions.simurgh.core.identity.security.config.JwtProperties;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

  private static final String KEY_ID = "simurgh-rsa-key-1";
  private static final String SESSION_ID_CLAIM = "sid";

  private final JwtEncoder jwtEncoder;
  private final JwtDecoder jwtDecoder;
  private final JwtProperties jwtProperties;
  private final Clock clock;

  public JwtService(
          JwtEncoder jwtEncoder,
          JwtDecoder jwtDecoder,
          JwtProperties jwtProperties,
          Clock clock
  ) {
    this.jwtEncoder = jwtEncoder;
    this.jwtDecoder = jwtDecoder;
    this.jwtProperties = jwtProperties;
    this.clock = clock;
  }

  public String generateAccessToken(UUID userId, UUID sessionId) {

    Instant issuedAt = clock.instant();
    Instant expiresAt = issuedAt.plus(jwtProperties.accessTokenTtl());

    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer(jwtProperties.issuer())
            .audience(List.of(jwtProperties.audience()))
            .subject(userId.toString())
            .issuedAt(issuedAt)
            .expiresAt(expiresAt)
            .id(UUID.randomUUID().toString())
            .claim(SESSION_ID_CLAIM, sessionId.toString())
            .build();

    JwsHeader header = JwsHeader
            .with(SignatureAlgorithm.RS256)
            .keyId(KEY_ID)
            .build();

    return jwtEncoder.encode(
            JwtEncoderParameters.from(header, claims)
    ).getTokenValue();
  }

  public Jwt decode(String token) {
    return jwtDecoder.decode(token);
  }

  public UUID extractUserId(String token) {
    return UUID.fromString(decode(token).getSubject());
  }

  public UUID extractSessionId(String token) {
    String sessionId = decode(token)
            .getClaimAsString(SESSION_ID_CLAIM);
    if (sessionId == null || sessionId.isBlank()) {
      throw new IllegalArgumentException(
              "Token does not contain a session ID"
      );
    }
    return UUID.fromString(sessionId);
  }

  public boolean isValid(String token) {
    try {
      decode(token);
      return true;
    } catch (JwtException | IllegalArgumentException exception) {
      return false;
    }
  }
}
