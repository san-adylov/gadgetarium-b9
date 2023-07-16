package peaksoft.house.gadgetariumb9.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.SecureRandom;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResetTokenConfig {

  private static final int KEY_LENGTH_BYTE = 32;

  @Bean
  public String generateSecretKey() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] keyBytes = new byte[KEY_LENGTH_BYTE];
    secureRandom.nextBytes(keyBytes);
    return Base64.getEncoder().encodeToString(keyBytes);
  }

  public String generateToken() {
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
    Key signingKey = new SecretKeySpec(DatatypeConverter.parseBase64Binary(generateSecretKey()),
        SignatureAlgorithm.HS256.getJcaName());
    Date expiryDate = Date.from(ZonedDateTime.now().plusMinutes(2).toInstant());
    return Jwts.builder()
        .setIssuedAt(now)
        .setExpiration(expiryDate)
        .signWith(signingKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token) {
    try {
      Key signingKey = new SecretKeySpec(DatatypeConverter.parseBase64Binary(generateSecretKey()),
          SignatureAlgorithm.HS256.getJcaName());
      Jws<Claims> claimsJws = Jwts.parserBuilder()
          .setSigningKey(signingKey)
          .build()
          .parseClaimsJws(token);
      Date expirationDate = claimsJws.getBody().getExpiration();
      return !expirationDate.before(new Date());
    } catch (JwtException e) {
      return false;
    }
  }
}