package peaksoft.house.gadgetariumb9.api;

import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.EmailRequest;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignUpRequest;
import peaksoft.house.gadgetariumb9.dto.response.simpleResponse.AuthenticationResponse;
import peaksoft.house.gadgetariumb9.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationApi {

  private final AuthenticationService authenticationService;

  @PostMapping("/signUp")
  public AuthenticationResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
    return authenticationService.signUp(signUpRequest);
  }

  @PostMapping("/signIn")
  public AuthenticationResponse signIn(@RequestBody @Valid  SignInRequest signInRequest) {
    return authenticationService.signIn(signInRequest);
  }

  @PostMapping("/forgotPassword")
  public void forgotPassword (@RequestParam String email ){
    authenticationService.forgotPassword(email);
  }
  @PostMapping("/resetPassword/{token}")
  public String resetPassword(@RequestParam String email,@PathVariable String token){
   return authenticationService.resetPassword(email,token);
  }

}
