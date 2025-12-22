package com.hhh.recipe_mn.dto.response;

import com.hhh.recipe_mn.utlis.Gender;
import com.hhh.recipe_mn.utlis.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Gender gender;
    private UserStatus status;

    private Set<String> roles;
    private Set<String> permissions;
}
