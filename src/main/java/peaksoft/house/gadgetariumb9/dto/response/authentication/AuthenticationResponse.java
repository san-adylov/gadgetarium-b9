package peaksoft.house.gadgetariumb9.dto.response.authentication;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationResponse {

    private String token;

    private String role;
}
