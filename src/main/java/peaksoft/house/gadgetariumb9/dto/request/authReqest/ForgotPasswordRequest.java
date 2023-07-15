package peaksoft.house.gadgetariumb9.dto.request.authReqest;

import peaksoft.house.gadgetariumb9.validation.password.Password;

public record ForgotPasswordRequest(
    @Password(message = "Wrong format password")
    String password,
    @Password(message = "Wrong format password")
    String confirmPassword
) {}