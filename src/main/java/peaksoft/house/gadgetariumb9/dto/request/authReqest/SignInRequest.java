package peaksoft.house.gadgetariumb9.dto.request.authReqest;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record SignInRequest
    (
        @Email(message = "Wrong format email")
        String email,
        String password
    ) {}