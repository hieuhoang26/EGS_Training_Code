package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.dto.request.LogInRequest;
import com.hhh.recipe_mn.dto.response.ResponseData;
import com.hhh.recipe_mn.dto.response.TokenResponse;

public interface AuthService {
    ResponseData<TokenResponse> login(LogInRequest request);
//    ResponseData<TokenResponse> signup(SignupRequest request);
//    ResponseData<TokenResponse> refreshToken(RefreshTokenRequest request);
//    ResponseData<Void> logout(String token);
}
