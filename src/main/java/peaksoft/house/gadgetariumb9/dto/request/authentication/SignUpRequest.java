package peaksoft.house.gadgetariumb9.dto.request.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import peaksoft.house.gadgetariumb9.validations.password.Password;
import peaksoft.house.gadgetariumb9.validations.phoneNumber.PhoneNumber;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "Name must not be empty")
    @NotNull(message = "Name must not be null")
    private String firstName;

    @NotBlank(message = "Surname must not be empty")
    @NotNull(message = "Surname must not be null")
    private String lastName;

    @PhoneNumber(message = "Wrong format phone number")
    private String phoneNumber;

    @Email(message = "Wrong format email")
    private String email;

    @Password(message = "Wrong format password")
    private String password;

}
