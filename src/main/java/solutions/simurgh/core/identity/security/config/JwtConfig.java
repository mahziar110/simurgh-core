
package solutions.simurgh.core.identity.security.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Clock;

@Configuration
public class JwtConfig {

  private static final String KEY_ID = "simurgh-rsa-key-1";

  @Bean
  public JwtEncoder jwtEncoder(
          RSAPublicKey publicKey,
          RSAPrivateKey privateKey
  ) {
    RSAKey rsaKey = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(KEY_ID)
            .build();
    ImmutableJWKSet<SecurityContext> jwkSource =
            new ImmutableJWKSet<>(new JWKSet(rsaKey));
    return new NimbusJwtEncoder(jwkSource);
  }

  @Bean
  public JwtDecoder jwtDecoder(
          RSAPublicKey publicKey,
          JwtProperties properties
  ) {
    NimbusJwtDecoder decoder = NimbusJwtDecoder
            .withPublicKey(publicKey)
            .signatureAlgorithm(SignatureAlgorithm.RS256)
            .build();
    OAuth2TokenValidator<Jwt> issuerValidator =
            JwtValidators.createDefaultWithIssuer(properties.issuer());
    OAuth2TokenValidator<Jwt> audienceValidator = jwt -> {
      if (jwt.getAudience().contains(properties.audience())) {
        return OAuth2TokenValidatorResult.success();
      }
      return OAuth2TokenValidatorResult.failure(
              new OAuth2Error(
                      "invalid_token",
                      "Required audience is missing",
                      null
              )
      );
    };
    decoder.setJwtValidator(
            new DelegatingOAuth2TokenValidator<>(
                    issuerValidator,
                    audienceValidator
            )
    );
    return decoder;
  }

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }
}
