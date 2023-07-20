package peaksoft.house.gadgetariumb9.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignUpRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;
import peaksoft.house.gadgetariumb9.services.AuthenticationService;
import peaksoft.house.gadgetariumb9.validations.password.Password;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication")
@CrossOrigin(maxAge = 3600,origins = "*")
@Validated
public class AuthenticationApi {

  private final AuthenticationService authenticationService;

  @PostMapping("/sign-up")
  @Operation(summary = "sign-up")
  public String signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
    return authenticationService.signUp(signUpRequest);
  }

  @PostMapping("/sign-in")
  @Operation(summary = "signIp")
  public String signIn(@RequestBody @Valid SignInRequest signInRequest) {
    return authenticationService.signIn(signInRequest);
  }

  @PostMapping("/forgot-password")
  @Operation(summary = "forgotPassword")
  public SimpleResponse forgotPassword (@RequestParam
                                        @Valid
                                        @Email
                                        String email,
                                        @RequestParam
                                        String link){
    return authenticationService.forgotPassword(email,link);
  }

  @PostMapping("/reset-password/{userId}")
  @Operation(summary = "resetPassword")
  public SimpleResponse resetPassword(@RequestParam
                                      @Valid
                                      @Password
                                      String password,
                                      @PathVariable
                                      Long userId){
   return authenticationService.resetPassword(password,userId);
  }
}
