package com.hhh.recipe_mn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhh.recipe_mn.dto.request.LogInRequest;
import com.hhh.recipe_mn.dto.request.SignUpRequest;
import com.hhh.recipe_mn.dto.response.TokenResponse;
import com.hhh.recipe_mn.security.JwtService;
import com.hhh.recipe_mn.service.AuthService;
import com.hhh.recipe_mn.service.UserService;
import com.hhh.recipe_mn.utlis.Uri;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AuthService authService;

    private LogInRequest loginRequest;
    private SignUpRequest signUpRequest;
    private TokenResponse tokenResponse;

    @BeforeEach
    void setUp() {
        loginRequest = new LogInRequest();
        loginRequest.setEmail("testuser");
        loginRequest.setPassword("password123");

        signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("newuser");
        signUpRequest.setPassword("newpassword123");
        signUpRequest.setEmail("newuser@example.com");

        tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("access-token-123");
        tokenResponse.setRefreshToken("refresh-token-456");
    }


    // LOGIN SUCCESS
    @Test
    void login_WithValidRequest_ShouldReturn200AndToken() throws Exception {
        when(authService.login(any(LogInRequest.class))).thenReturn(tokenResponse);

        mockMvc.perform(post(Uri.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successfully"))
                .andExpect(jsonPath("$.data.accessToken").value("access-token-123"))
                .andExpect(jsonPath("$.data.refreshToken").value("refresh-token-456"));
    }

    // LOGIN FAILURE
    @Test
    void login_WhenServiceThrowsException_ShouldReturn400() throws Exception {
        when(authService.login(any(LogInRequest.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        mockMvc.perform(post(Uri.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(jsonPath("$.message").value("Update user fail"))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }

    // SIGNUP SUCCESS

    @Test
    void signUp_WithValidRequest_ShouldReturn200() throws Exception {
        UUID expectedUserId = UUID.randomUUID();
        when(authService.signUp(any(SignUpRequest.class))).thenReturn(expectedUserId);

        mockMvc.perform(post(Uri.SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User has create successfully"))
                .andExpect(jsonPath("$.data").value(expectedUserId.toString()));
    }

    // SIGNUP FAILURE
    @Test
    void signUp_WhenServiceThrowsException_ShouldReturn400() throws Exception {
        when(authService.signUp(any(SignUpRequest.class)))
                .thenThrow(new RuntimeException("User already exists"));

        mockMvc.perform(post(Uri.SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(jsonPath("$.message").value("Create user fail"))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }

    // REFRESH SUCCESS
    @Test
    void refresh_WithValidRequest_ShouldReturn200AndToken() throws Exception {
        when(authService.refresh(any(HttpServletRequest.class))).thenReturn(tokenResponse);

        mockMvc.perform(post(Uri.REFRESH)
                        .header("Authorization", "Bearer dummy-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Refresh"))
                .andExpect(jsonPath("$.data.accessToken").value("access-token-123"));
    }

    // REFRESH FAILURE

    @Test
    void refresh_WhenServiceThrowsException_ShouldReturn400() throws Exception {
        when(authService.refresh(any(HttpServletRequest.class)))
                .thenThrow(new RuntimeException("Invalid refresh token"));

        mockMvc.perform(post(Uri.REFRESH)
                        .header("Authorization", "Bearer dummy-token"))
                .andExpect(jsonPath("$.message").value("fail"))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }

    // NULL REQUEST HANDLING
    @Test
    void login_NullRequest_ShouldReturn400() throws Exception {
        when(authService.login(null))
                .thenThrow(new IllegalArgumentException("Request cannot be null"));

        mockMvc.perform(post(Uri.LOGIN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUp_NullRequest_ShouldReturn400() throws Exception {
        when(authService.signUp(null))
                .thenThrow(new IllegalArgumentException("Request cannot be null"));

        mockMvc.perform(post(Uri.SIGNUP)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void refresh_WithInvalidToken_ShouldReturn400() throws Exception {
        when(authService.refresh(any(HttpServletRequest.class)))
                .thenThrow(new RuntimeException("Invalid token"));

        mockMvc.perform(post(Uri.REFRESH)
                        .header("Authorization", "Bearer invalid-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("fail"))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }
}
