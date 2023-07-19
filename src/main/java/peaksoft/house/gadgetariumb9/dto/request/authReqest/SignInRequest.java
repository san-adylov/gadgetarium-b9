package peaksoft.house.gadgetariumb9.dto.request.authReqest;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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