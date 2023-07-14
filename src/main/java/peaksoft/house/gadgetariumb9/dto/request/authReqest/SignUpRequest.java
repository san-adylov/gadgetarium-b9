package peaksoft.house.gadgetariumb9.dto.request.authReqest;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import peaksoft.house.gadgetariumb9.validation.password.Password;
import peaksoft.house.gadgetariumb9.validation.phoneNumber.PhoneNumber;

@Builder
public record SignUpRequest(
    String firstName,
    String lastName,
    @PhoneNumber(message = "Wrong format phone number")
    String phoneNumber,
    @Email(message = "Wrong format email")
    String email,
    @Password(message = "Wrong format password")
    String password,
    @Password(message = "Wrong format password")
    String confirmPassword)
{}