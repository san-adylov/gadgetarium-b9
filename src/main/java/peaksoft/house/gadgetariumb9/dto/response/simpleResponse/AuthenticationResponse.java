package peaksoft.house.gadgetariumb9.dto.response.simpleResponse;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record AuthenticationResponse(
    String token,
    String email
) {}