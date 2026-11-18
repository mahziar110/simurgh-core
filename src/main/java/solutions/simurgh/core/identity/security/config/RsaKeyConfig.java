
package solutions.simurgh.core.identity.security.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class RsaKeyConfig {

  @Bean
  public RSAPrivateKey rsaPrivateKey(JwtProperties properties)
          throws IOException, GeneralSecurityException {
    return RsaKeyLoader.loadPrivateKey(
            (Resource) properties.privateKeyLocation()
    );
  }

  @Bean
  public RSAPublicKey rsaPublicKey(JwtProperties properties)
          throws IOException, GeneralSecurityException {
    return RsaKeyLoader.loadPublicKey(
            (Resource) properties.publicKeyLocation()
    );
  }
}
