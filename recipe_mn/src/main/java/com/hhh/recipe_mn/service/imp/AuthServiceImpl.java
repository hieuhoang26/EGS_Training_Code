package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.dto.request.LogInRequest;
import com.hhh.recipe_mn.dto.response.ResponseData;
import com.hhh.recipe_mn.dto.response.TokenResponse;
import com.hhh.recipe_mn.repository.UserRepository;
import com.hhh.recipe_mn.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;


    @Override
    public ResponseData<TokenResponse> login(LogInRequest request) {
        return null;
    }
}
