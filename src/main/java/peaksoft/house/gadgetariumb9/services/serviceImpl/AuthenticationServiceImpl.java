package peaksoft.house.gadgetariumb9.services.serviceImpl;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.authentication.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authentication.SignUpRequest;
import peaksoft.house.gadgetariumb9.dto.response.authentication.AuthenticationResponse;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.enums.Role;
import peaksoft.house.gadgetariumb9.exceptions.AlreadyExistException;
import peaksoft.house.gadgetariumb9.exceptions.BadCredentialException;
import peaksoft.house.gadgetariumb9.exceptions.NotFoundException;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import peaksoft.house.gadgetariumb9.services.AuthenticationService;

@Slf4j
@Service
@RequiredArgsConstructor
public class    AuthenticationServiceImpl implements AuthenticationService {

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

    @Value("${stripe.apikey}")
    private String API_KEY;

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            log.error("User with email: %s already exist".formatted(signUpRequest.getEmail()));
            throw new AlreadyExistException(
                    "User with email: %s already exist".formatted(signUpRequest.getEmail()));
        }
        if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())){
            log.error("User with phone number: %s already exist".formatted(signUpRequest.getPhoneNumber()));
            throw new AlreadyExistException(
                "User with phone number: %s already exist".formatted(signUpRequest.getPhoneNumber()));
        }
        User user = User
                .builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(Role.USER)
                .isSubscription(false)
                .build();
        log.info("User successfully saved");
        userRepository.save(user);
        log.info("Generation of a token for a new user");
        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.getEmail())
                .orElseThrow(() -> {
                    log.error("User with email: %s not found".formatted(signInRequest.getEmail()));
                    return new NotFoundException(
                            "User with email: %s not found".formatted(signInRequest.getEmail()));
                });
        if (signInRequest.getPassword().isBlank()) {
            log.error("Password is blank!");
            throw new BadCredentialException("Email is blank!");
        }
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            log.error("Wrong password");
            throw new BadCredentialException("Wrong password!");
        }
        log.info("Generation of a token for a registered user");
        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .role(user.getRole().name())
                .build();
    }

    @Override
    public SimpleResponse forgotPassword(String email, String link) {
        User user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email: %s not found".formatted(email));
                    return new NotFoundException("User with email: %s not found".formatted(email));
                });
        String emailBody = "Для сброса пароля перейдите по ссылке: " + link + "/" + user.getId();
        sendEmail(user.getEmail(), emailBody);
        log.info("Message has been sent to your email");
        return SimpleResponse
                .builder()
                .message("Message has been sent to your email")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public SimpleResponse resetPassword(String password, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> {
                            log.error("User with id: %s not found".formatted(userId));
                            return new NotFoundException("User with id: %s not found".formatted(userId));
                        });
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        log.info("Password successfully updated");
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
    public void addAdmin() {
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
            log.info("Admin saved");
        }
        setup();

    }

    private void setup() {
        Stripe.apiKey = API_KEY;
        log.info("Api key");
    }
}
