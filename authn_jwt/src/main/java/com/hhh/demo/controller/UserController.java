package com.hhh.demo.controller;

import com.hhh.demo.dto.LogInRequest;
import com.hhh.demo.dto.TokenResponse;
import com.hhh.demo.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @GetMapping("/")
    public String hello(Authentication authentication) {
        return "Hello, " + authentication.getName() + "!";
    }



    @PostMapping("/auth/login")
    public TokenResponse login(@RequestBody LogInRequest request) {
        Authentication auth =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        return tokenService.generateTokens(auth);
    }

//    @PostMapping("/refresh")
//    public TokenResponse refresh(@RequestBody RefreshRequest request) {
//        return tokenService.refresh(request.getRefreshToken());
//    }

}
