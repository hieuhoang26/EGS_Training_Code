package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.dto.request.LogInRequest;
import com.hhh.recipe_mn.dto.request.SignUpRequest;
import com.hhh.recipe_mn.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

public interface AuthService {
    TokenResponse login(LogInRequest logInRequest);

    UUID signUp(SignUpRequest logInRequest);

    TokenResponse refresh(HttpServletRequest refreshToken);

}
