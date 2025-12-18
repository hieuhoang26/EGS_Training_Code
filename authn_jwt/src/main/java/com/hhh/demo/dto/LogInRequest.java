package com.hhh.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequest {
    @NotBlank(message = "Username must be not null")
    private String username;

    @NotBlank(message = "password must be not blank")
    private String password;

}
