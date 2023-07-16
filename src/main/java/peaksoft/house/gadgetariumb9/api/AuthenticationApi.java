package peaksoft.house.gadgetariumb9.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignUpRequest;
import peaksoft.house.gadgetariumb9.service.AuthenticationService;
import peaksoft.house.gadgetariumb9.validation.password.Password;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationApi {

  private final AuthenticationService authenticationService;

  @PostMapping("/signUp")
  @Operation(summary = "signUp")
  public String signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
    return authenticationService.signUp(signUpRequest);
  }

  @PostMapping("/signIn")
  @Operation(summary = "signIp")
  public String signIn(@RequestBody @Valid SignInRequest signInRequest) {
    return authenticationService.signIn(signInRequest);
  }

  @PostMapping("/forgotPassword")
  @Operation(summary = "forgotPassword")
  public String forgotPassword (
      @RequestParam
      @Valid
      @Email
      String email ){
    return authenticationService.forgotPassword(email);
  }
  @PostMapping("/resetPassword")
  @Operation(summary = "resetPassword")
  public String resetPassword(
      @RequestParam
      @Valid
      @Password
      String password,
      @RequestParam
      String token){
   return authenticationService.resetPassword(password,token);
  }

}
