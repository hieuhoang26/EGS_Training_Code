package com.hhh.keycloak_ex.service;

import com.hhh.keycloak_ex.dto.request.RegistrationRequest;
import com.hhh.keycloak_ex.dto.request.TokenExchangeParam;
import com.hhh.keycloak_ex.dto.request.UserCreationParam;
import com.hhh.keycloak_ex.repository.IdentityClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Slf4j

@Service
@RequiredArgsConstructor
public class UserService {

    private final IdentityClient identityClient;

    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    public String register(RegistrationRequest request) {
        try {
            // Exchange client Token
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());
            log.info("TokenInfo {}", token.getAccessToken());

            // Create user with client Token and given info
            var creationResponse = identityClient.createUser(
                    "Bearer " + token.getAccessToken(),
                    UserCreationParam.builder()
                            .username(request.getUsername())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .email(request.getEmail())
                            .enabled(true)
                            .emailVerified(false)
                            .build());

            String userId = extractUserId(creationResponse);
            log.info("UserId {}", userId);
            // DB ...
            return userId;
        } catch (FeignException exception) {
            log.info(exception.getMessage());
        }
        return null;
    }


    private String extractUserId(ResponseEntity<?> response) {
        String location = response.getHeaders().get("Location").get(0);
        String[] splitedStr = location.split("/");
        return splitedStr[splitedStr.length - 1];
    }

}
