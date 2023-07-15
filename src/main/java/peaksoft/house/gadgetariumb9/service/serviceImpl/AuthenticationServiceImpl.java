package peaksoft.house.gadgetariumb9.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.house.gadgetariumb9.config.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignUpRequest;
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
}
