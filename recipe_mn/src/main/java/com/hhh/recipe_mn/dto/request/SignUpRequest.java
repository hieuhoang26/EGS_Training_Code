package com.hhh.recipe_mn.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
