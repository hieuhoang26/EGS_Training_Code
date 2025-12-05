package org.example.search_advance.dto.response;


import lombok.*;
import org.example.search_advance.util.UserStatus;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicInfoDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserStatus status;

    private String fullName;
}
