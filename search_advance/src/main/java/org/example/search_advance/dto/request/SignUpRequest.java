package org.example.search_advance.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest {

    private String phone;

    @Email
    @Length(max = 50)
    private String email;

    @Length(max = 1000, min = 4)
    String password;

    @Length(max = 50)
    private String username;

    private String role;
}
