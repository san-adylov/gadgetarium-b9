package peaksoft.house.gadgetariumb9.dto.request.authentication;

import jakarta.validation.constraints.Email;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    @Email(message = "Wrong format email")
    private String email;

    private String password;
}