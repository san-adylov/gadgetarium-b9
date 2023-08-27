package peaksoft.house.gadgetariumb9.services.serviceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import peaksoft.house.gadgetariumb9.config.security.JwtService;
import peaksoft.house.gadgetariumb9.dto.request.authentication.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authentication.SignUpRequest;
import peaksoft.house.gadgetariumb9.dto.response.authentication.AuthenticationResponse;
import peaksoft.house.gadgetariumb9.enums.Role;
import peaksoft.house.gadgetariumb9.exceptions.AlreadyExistException;
import peaksoft.house.gadgetariumb9.models.User;
import peaksoft.house.gadgetariumb9.repositories.UserRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUp_NewUser_Success() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setFirstName("Tom");
        signUpRequest.setLastName("Hanks");
        signUpRequest.setPhoneNumber("+996709121314");
        signUpRequest.setEmail("tom@gmail.com");
        signUpRequest.setPassword("Tom123");

        when(userRepository.existsByEmail("tom@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("Tom123")).thenReturn("encodedPassword");

        User savedUser = new User();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        String generatedToken = jwtService.generateToken(savedUser); // Ожидаемый токен
        when(jwtService.generateToken(savedUser)).thenReturn(generatedToken);

        AuthenticationResponse response = authenticationService.signUp(signUpRequest);

        assertNotNull(response);
        assertEquals(generatedToken, response.getToken());
        assertEquals(Role.USER.name(), response.getRole());

        verify(userRepository, times(1)).existsByEmail("tom@gmail.com");
        verify(passwordEncoder, times(1)).encode("Tom123");
        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(savedUser);
    }

    @Test
    void signUp_ExistingUser_ThrowsAlreadyExistException() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("tom@gmail.com");

        when(userRepository.existsByEmail("tom@gmail.com")).thenReturn(true);

        assertThrows(AlreadyExistException.class, () -> authenticationService.signUp(signUpRequest));

        verify(userRepository, times(1)).existsByEmail("tom@gmail.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    void signIn() {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("tom@gmail.com");
        signInRequest.setPassword("Tom123");

        User user = new User();
        user.setEmail("tom@gmail.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);

        when(userRepository.getUserByEmail("tom@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Tom123", "encodedPassword")).thenReturn(true);

        String generatedToken = "";
        when(jwtService.generateToken(user)).thenReturn(generatedToken);

        AuthenticationResponse response = authenticationService.signIn(signInRequest);

        assertNotNull(response);
        assertEquals(generatedToken, response.getToken());
        assertEquals(Role.USER.name(), response.getRole());

        verify(userRepository, times(1)).getUserByEmail("tom@gmail.com");
        verify(passwordEncoder, times(1)).matches("Tom123", "encodedPassword");
        verify(jwtService, times(1)).generateToken(user);
    }
}