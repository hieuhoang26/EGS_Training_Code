package com.hhh.recipe_mn.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequest {
    @NotBlank(message = "Email must be not null")
    private String email;

    @NotBlank(message = "username must be not blank")
    private String password;

}
