package org.example.search_advance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.search_advance.dto.request.LogInRequest;
import org.example.search_advance.dto.request.SignUpRequest;
import org.example.search_advance.dto.response.ResponseData;
import org.example.search_advance.dto.response.ResponseError;
import org.example.search_advance.dto.response.TokenResponse;
import org.example.search_advance.service.AuthService;
import org.example.search_advance.util.Uri;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Validated
@RestController
@Tag(name = "Authentication Controller")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

//    @PostMapping(Uri.LOGIN)
//    public ResponseData login(@RequestBody LogInRequest request) {
//        try {
//            TokenResponse rs = authService.login(request);
//            log.info(rs.toString());
//            return new ResponseData(OK.value(), "Login successfully", rs);
//        } catch (Exception e) {
//            log.error("errorMessage={}", e.getMessage(), e.getCause());
//            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update user fail");
//        }
//    }
//
//    @PostMapping(Uri.SIGNUP)
//    public ResponseData signUp(@RequestBody SignUpRequest request) {
//        try {
//            return new ResponseData<>(OK.value(), "User has create successfully", authService.signUp(request));
//        } catch (Exception e) {
//            log.error("errorMessage={}", e.getMessage(), e.getCause());
//            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create user fail");
//        }
//    }
//
//    @PostMapping(Uri.REFRESH)
//    public ResponseData<TokenResponse> refresh(HttpServletRequest request) {
//        try {
//            return new ResponseData<>(OK.value(), "Refresh", authService.refresh(request));
//        } catch (Exception e) {
//            log.error("errorMessage={}", e.getMessage(), e.getCause());
//            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "fail");
//        }
//    }
}
