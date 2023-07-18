package peaksoft.house.gadgetariumb9.service.serviceImpl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignUpRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
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
  public SimpleResponse forgotPassword(String email, String link) {
    User user = userRepository.getUserByEmail(email)
        .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email)));
    String emailBody = "Для сброса перейдите по ссылке: " + link + "/" + user.getId();
    sendEmail(user.getEmail(), emailBody);
    return SimpleResponse
        .builder()
        .message("Message has been sent to your Email")
        .status(HttpStatus.OK)
        .build();
  }

  @Override
  public SimpleResponse resetPassword(String password, Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(
            () -> new TokenExpiredException("User with id: %s not found".formatted(userId)));
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
    return SimpleResponse
        .builder()
        .message("Password successfully updated")
        .status(HttpStatus.OK)
        .build();
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