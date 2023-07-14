package peaksoft.house.gadgetariumb9.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class
AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Override
  public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
    if (userRepository.existsByEmail(signUpRequest.email())) {
      log.error("User with email: %s already exist!".formatted(signUpRequest.email()));
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
        .orElseThrow(() ->
        {
          log.error("User with email: %s not found".formatted(signInRequest.email()));
          return new NotFoundException(
              "User with email: %s not found".formatted(signInRequest.email()));
        });
    if (signInRequest.password().isBlank()) {
      throw new BadCredentialException("Password is blank!");
    }
    if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
      throw new BadCredentialException("Wrong password!");
    }
    String token = jwtService.generateToken(user);
    jwtService.extractUsername()
    return AuthenticationResponse
        .builder()
        .email()
        .token(token)
        .build();
  }
}
