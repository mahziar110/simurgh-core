package solutions.simurgh.core.identity.security.config;

import com.nimbusds.jose.util.Resource;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "simurgh.security.jwt")
public record JwtProperties(
        String issuer,
        String audience,
        Duration accessTokenTtl,
        Resource privateKeyLocation,
        Resource publicKeyLocation
) {
}