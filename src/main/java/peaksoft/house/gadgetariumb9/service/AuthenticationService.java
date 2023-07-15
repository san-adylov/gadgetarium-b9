package peaksoft.house.gadgetariumb9.service;

import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authReqest.SignUpRequest;

public interface AuthenticationService {

  String signUp(SignUpRequest signUpRequest);

  String signIn(SignInRequest signInRequest);

}
