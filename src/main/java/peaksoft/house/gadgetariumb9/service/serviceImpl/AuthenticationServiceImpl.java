package peaksoft.house.gadgetariumb9.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.JwtService;
import peaksoft.house.gadgetariumb9.config.ResetTokenConfig;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignUpRequest;
import peaksoft.house.gadgetariumb9.entities.User;
import peaksoft.house.gadgetariumb9.enums.Role;
import peaksoft.house.gadgetariumb9.exception.AlreadyExistException;
import peaksoft.house.gadgetariumb9.exception.BadCredentialException;
import peaksoft.house.gadgetariumb9.exception.NotFoundException;
import peaksoft.house.gadgetariumb9.exception.TokenExpiredException;
import peaksoft.house.gadgetariumb9.repository.UserRepository;
import peaksoft.house.gadgetariumb9.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final MailSender mailSender;
  private final ResetTokenConfig resetTokenConfig;

  @Value("${spring.admin_password}")
  private String PASSWORD;

  @Value("${spring.admin_email}")
  private String EMAIL;

  @Value("${spring.mail.username}")
  private String EMAIL_FROM;

  @Override
  public String signUp(SignUpRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.email())) {
      throw new AlreadyExistException(
          "User with email: %s already exist".formatted(signUpRequest.email()));
    }
    User user = User
        .builder()
        .firstName(signUpRequest.firstName())
        .lastName(signUpRequest.lastName())
        .email(signUpRequest.email())
        .phoneNumber(signUpRequest.phoneNumber())
        .password(passwordEncoder.encode(signUpRequest.password()))
        .role(Role.USER)
        .isSubscription(false)
        .build();
    userRepository.save(user);
    return jwtService.generateToken(user);
  }

  @Override
  public String signIn(SignInRequest signInRequest) {
    User user = userRepository.getUserByEmail(signInRequest.email())
        .orElseThrow(() -> new NotFoundException(
            "User with email: %s not found".formatted(signInRequest.email())));
    if (signInRequest.password().isBlank()) {
      throw new BadCredentialException("Password is blank!");
    }
    if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
      throw new BadCredentialException("Wrong password!");
    }
    return jwtService.generateToken(user);
  }

  @Override
  public String forgotPassword(String email) {
    User user = userRepository.getUserByEmail(email)
        .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email)));
    String resetToken = (resetTokenConfig.generateToken());
    user.setResetToken(resetToken);
    userRepository.save(user);
    String resetLink =
        "http://localhost:9090/swagger-ui/index.html#/Authentication/resetPassword/?token="
            + resetToken;
    String emailBody = "Для сброса перейдите по ссылке: " + resetLink;
    sendEmail(user.getEmail(), emailBody);
    return "Message has been sent to your Email";
  }

  @Override
  public String resetPassword(String password, String token) {
    int index = token.indexOf("=");
    String newToken = "";
    if (index != -1) {
      newToken = token.substring(index + 1);
    }
    User user = userRepository.getUserByResetToken(newToken)
        .orElseThrow(() -> new TokenExpiredException("Token invalid!"));
    if (resetTokenConfig.isTokenValid(newToken)) {
      user.setPassword(passwordEncoder.encode(password));
      user.setResetToken(null);
      userRepository.save(user);
      return "Password successfully updated";
    } else {
      throw new TokenExpiredException("Token is expired");
    }
  }

  private void sendEmail(String to, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(EMAIL_FROM);
    message.setTo(to);
    message.setText(body);
    message.setSubject("Reset password");
    mailSender.send(message);
  }

  @PostConstruct
  private void addAdmin() {
    if (!userRepository.existsByEmail(EMAIL)) {
      User user = User
          .builder()
          .firstName("Admin")
          .lastName("Admin")
          .email(EMAIL)
          .phoneNumber(null)
          .password(passwordEncoder.encode(PASSWORD))
          .role(Role.ADMIN)
          .isSubscription(false)
          .build();
      userRepository.save(user);
    }
  }
}