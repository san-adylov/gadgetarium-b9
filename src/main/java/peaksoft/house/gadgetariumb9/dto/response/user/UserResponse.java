package peaksoft.house.gadgetariumb9.dto.response.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String image;
}
