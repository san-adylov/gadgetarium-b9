package peaksoft.house.gadgetariumb9.services;

import peaksoft.house.gadgetariumb9.dto.request.authentication.SignInRequest;
import peaksoft.house.gadgetariumb9.dto.request.authentication.SignUpRequest;
import peaksoft.house.gadgetariumb9.dto.simple.SimpleResponse;

public interface AuthenticationService {

    String signUp(SignUpRequest signUpRequest);

    String signIn(SignInRequest signInRequest);

    SimpleResponse forgotPassword(String email, String link);

    SimpleResponse resetPassword(String password, Long userId);
}
