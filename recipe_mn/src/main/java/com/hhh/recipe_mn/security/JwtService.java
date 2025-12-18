package com.hhh.recipe_mn.security;

import com.hhh.recipe_mn.model.User;
import com.hhh.recipe_mn.utlis.TokenType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String generateToken(UserDetails user);
    String generateRefreshToken(UserDetails user);
    String generateResetToken(UserDetails user);
    String extractUsername(String token, TokenType type);
    boolean isValid(String token, TokenType type, UserDetails userDetails);
    boolean validateToken(String token, TokenType type);
}
