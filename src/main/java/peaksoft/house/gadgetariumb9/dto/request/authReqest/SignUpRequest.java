package peaksoft.house.gadgetariumb9.dto.request.authReqest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import peaksoft.house.gadgetariumb9.validation.password.Password;
import peaksoft.house.gadgetariumb9.validation.phoneNumber.PhoneNumber;

@Builder
public record SignUpRequest(
    @NotBlank(message = "Name must not be empty")
    @NotNull(message = "Name must not be null")
    String firstName,

    @NotBlank(message = "Surname must not be empty")
    @NotNull(message = "Surname must not be null")
    String lastName,

    @PhoneNumber(message = "Wrong format phone number")
    String phoneNumber,

    @Email(message = "Wrong format email")
    String email,

    @Password(message = "Wrong format password")
    String password
) {

}
