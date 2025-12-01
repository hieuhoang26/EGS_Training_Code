package org.example.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPost {
    private int userId;
    private String username;
    private String email;

    private int postId;
    private String title;
}
