package peaksoft.house.gadgetariumb9.service.serviceImpl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignUpRequest;
import peaksoft.house.gadgetariumb9.dto.response.simpleResponse.AuthenticationResponse;
import peaksoft.house.gadgetariumb9.entities.User;
import peaksoft.house.gadgetariumb9.enums.Role;
import peaksoft.house.gadgetariumb9.exception.AlreadyExistException;
import peaksoft.house.gadgetariumb9.exception.BadCredentialException;
import peaksoft.house.gadgetariumb9.exception.NotFoundException;
import peaksoft.house.gadgetariumb9.repository.UserRepository;
import peaksoft.house.gadgetariumb9.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final JavaMailSender javaMailSender;

  @Override
  public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
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
    return AuthenticationResponse
        .builder()
        .token(jwtService.generateToken(user))
        .build();
  }

  @Override
  public AuthenticationResponse signIn(SignInRequest signInRequest) {
    User user = userRepository.getUserByEmail(signInRequest.email())
        .orElseThrow(() -> new NotFoundException(
            "User with email: %s not found".formatted(signInRequest.email())));
    if (signInRequest.password().isBlank()) {
      throw new BadCredentialException("Password is blank!");
    }
    if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
      throw new BadCredentialException("Wrong password!");
    }
    String token = jwtService.generateToken(user);
    return AuthenticationResponse
        .builder()
        .token(token)
        .build();
  }

  @Override
  public String forgotPassword(String email) {
    User user = userRepository.getUserByEmail(email)
        .orElseThrow(() -> new NotFoundException("fdjs"));
    String resetToken = generateResetToken();
    user.setResetToken(resetToken);
    userRepository.save(user);
    String resetLink =
        "http://localhost:9090/swagger-ui/index.html#/authentication-api/resetPassword/?token="
            + resetToken;
    String emailBody = "Для сброса перейдите по ссылке: " + resetLink;
    sendEmail(user.getEmail(), emailBody);
    return "Message has been sent to your Email";
  }

  @Override
  public String resetPassword(String email, String token) {
    User user = userRepository.getUserByEmailAndResetToken(email, token)
        .orElseThrow(() -> new NotFoundException("User with email: %s not found".formatted(email)));
    return user.getEmail();
  }

  private String generateResetToken() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  private void sendEmail(String to, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("sanadylov@gmail.com");
    message.setTo(to);
    message.setText(body);
    message.setSubject("Reset password");
    javaMailSender.send(message);

  }


}
