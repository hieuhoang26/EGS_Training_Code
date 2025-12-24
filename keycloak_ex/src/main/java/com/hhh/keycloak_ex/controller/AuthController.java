package com.hhh.keycloak_ex.controller;


import com.hhh.keycloak_ex.dto.request.RegistrationRequest;
import com.hhh.keycloak_ex.dto.response.ResponseData;
import com.hhh.keycloak_ex.service.UserService;
import com.hhh.keycloak_ex.utils.Uri;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(Uri.ADMIN)
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello from secured API");
    }

    @GetMapping("/me")
    public Authentication me(Authentication auth) {
        return auth;
    }

    @PostMapping("/register")
    public ResponseData<?> register(@RequestBody RegistrationRequest request) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", userService.register(request));
    }
}
