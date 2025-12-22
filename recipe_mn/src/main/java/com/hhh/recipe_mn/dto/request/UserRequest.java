package com.hhh.recipe_mn.dto.request;

import com.hhh.recipe_mn.utlis.Gender;
import com.hhh.recipe_mn.utlis.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String password;

    private String phone;
    private Gender gender;
    private UserStatus status;

    private Set<UUID> roleIds;
}
