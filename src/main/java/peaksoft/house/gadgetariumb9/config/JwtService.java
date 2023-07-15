package peaksoft.house.gadgetariumb9.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.entities.User;
import peaksoft.house.gadgetariumb9.exception.NotFoundException;
import peaksoft.house.gadgetariumb9.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final UserRepository userRepository;

  @Value("${spring.jwt.secret_key}")
  String SECRET_KEY;

  public String generateToken(UserDetails userDetails) {
    return JWT.create()
        .withClaim("username", userDetails.getUsername())
        .withIssuedAt(new Date())
        .withExpiresAt(Date.from(ZonedDateTime.now().plusHours(24).toInstant()))
        .sign(Algorithm.HMAC512(SECRET_KEY));
  }

  public String validateToken(String token) {
    JWTVerifier jwtVerifier =
        JWT
            .require(Algorithm.HMAC512(SECRET_KEY))
            .build();
    DecodedJWT jwt = jwtVerifier.verify(token);

    return jwt.getClaim("username").asString();
  }

  public User getAuthentication() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.getUserByEmail(email)
        .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email)));
  }
}