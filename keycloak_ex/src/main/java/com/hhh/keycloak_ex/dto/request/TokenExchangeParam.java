package com.hhh.keycloak_ex.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenExchangeParam {
    private String grant_type;
    private String client_id;
    private String client_secret;
    private String scope;
}