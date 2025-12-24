package com.hhh.keycloak_ex.dto.response;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UserResponse {
    String profileId;
    String userId;
    String email;
    String username;
    String firstName;
    String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dob;
}
