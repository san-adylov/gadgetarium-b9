package peaksoft.house.gadgetariumb9.service;

import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignUpRequest;
import peaksoft.house.gadgetariumb9.dto.response.simpleResponse.AuthenticationResponse;

public interface AuthenticationService {

  AuthenticationResponse signUp(SignUpRequest signUpRequest);

  AuthenticationResponse signIn(SignInRequest signInRequest);

  String forgotPassword(String email);
  String resetPassword(String email,String token);

}
