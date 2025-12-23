package com.hhh.recipe_mn.controller;

import com.hhh.recipe_mn.aop.LogExecution;
import com.hhh.recipe_mn.dto.request.LogInRequest;
import com.hhh.recipe_mn.dto.request.SignUpRequest;
import com.hhh.recipe_mn.dto.response.ResponseData;
import com.hhh.recipe_mn.dto.response.ResponseError;
import com.hhh.recipe_mn.dto.response.TokenResponse;
import com.hhh.recipe_mn.service.AuthService;
import com.hhh.recipe_mn.utlis.Uri;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping(Uri.LOGIN)
    @LogExecution
    public ResponseData login(@RequestBody LogInRequest request) {
        try {
            TokenResponse rs = authService.login(request);
            log.info(rs.toString());
            return new ResponseData(OK.value(), "Login successfully", rs);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update user fail");
        }
    }

    @PostMapping(Uri.SIGNUP)
    public ResponseData signUp(@RequestBody SignUpRequest request) {
        try {
            return new ResponseData<>(OK.value(), "User has create successfully", authService.signUp(request));
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create user fail");
        }
    }

    @PostMapping(Uri.REFRESH)
    public ResponseData<TokenResponse> refresh(HttpServletRequest request) {
        try {
            return new ResponseData<>(OK.value(), "Refresh", authService.refresh(request));
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "fail");
        }
    }
}
