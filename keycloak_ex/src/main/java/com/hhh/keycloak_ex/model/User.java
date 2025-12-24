package com.hhh.keycloak_ex.model;

import java.time.LocalDate;

public class User {
    String id;
    // UserId from keycloak
    String userId;
    String email;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
}
