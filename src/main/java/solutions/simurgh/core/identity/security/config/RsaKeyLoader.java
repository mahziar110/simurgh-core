package solutions.simurgh.core.identity.security.config;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class RsaKeyLoader {

  private RsaKeyLoader() {
  }

  public static RSAPrivateKey loadPrivateKey(Resource resource)
          throws IOException, GeneralSecurityException {

    String pem = readPem(resource);

    String content = pem
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replaceAll("\\s", "");

    byte[] decoded = Base64.getDecoder().decode(content);

    PKCS8EncodedKeySpec keySpec =
            new PKCS8EncodedKeySpec(decoded);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
  }

  public static RSAPublicKey loadPublicKey(Resource resource)
          throws IOException, GeneralSecurityException {

    String pem = readPem(resource);

    String content = pem
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replaceAll("\\s", "");

    byte[] decoded = Base64.getDecoder().decode(content);

    X509EncodedKeySpec keySpec =
            new X509EncodedKeySpec(decoded);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    return (RSAPublicKey) keyFactory.generatePublic(keySpec);
  }

  private static String readPem(Resource resource) throws IOException {
    try (InputStream inputStream = resource.getInputStream()) {
      return new String(
              inputStream.readAllBytes(),
              StandardCharsets.UTF_8
      );
    }
  }
}