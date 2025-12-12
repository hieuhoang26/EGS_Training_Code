package org.example.search_advance.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    private String deviceToken;

    private String version;

}
