package peaksoft.house.gadgetariumb9.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String oldPassword;

    private String newPassword;

    private String imageLink;

}
