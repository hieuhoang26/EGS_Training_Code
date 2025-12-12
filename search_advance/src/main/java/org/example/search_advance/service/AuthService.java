package org.example.search_advance.service;

import jakarta.servlet.http.HttpServletRequest;
import org.example.search_advance.dto.request.LogInRequest;
import org.example.search_advance.dto.request.SignUpRequest;
import org.example.search_advance.dto.response.ResponseData;
import org.example.search_advance.dto.response.TokenResponse;
import org.example.search_advance.model.User;

public interface AuthService {
    TokenResponse login(LogInRequest logInRequest);

    Long signUp(SignUpRequest logInRequest);

    TokenResponse refresh(HttpServletRequest refreshToken);

}
