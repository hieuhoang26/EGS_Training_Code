package org.example.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int id;
    private String username;
    private String email;
    private String pass;
    private LocalDateTime createdAt;

    public User(String username, String mail, String pass) {
        this.username = username;
        this.email = mail;
        this.pass = pass;
    }
}
