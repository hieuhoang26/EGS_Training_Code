package com.hhh.recipe_mn.security;

import com.hhh.recipe_mn.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;

import java.util.Map;

public interface JwtService {
    String generateAccessToken(Authentication authentication, UserDetails user);
    String generateRefreshToken(Authentication authentication, UserDetails user);
    boolean isValid(String token,UserDetails user);
    String extractUsername(String token);
//    Map<String, Object> extractAllClaims(String token);
}
