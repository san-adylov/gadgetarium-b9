package peaksoft.house.gadgetariumb9.dto.response.simpleResponse;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
    String token
) {}