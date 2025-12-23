package com.hhh.recipe_mn.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse implements Serializable {
    private String id;
    private String username;
    private String phone;
    private List<String> roles;
    private List<String> permissions;
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

}

