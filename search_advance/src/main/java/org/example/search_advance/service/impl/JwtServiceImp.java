package org.example.search_advance.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.search_advance.model.User;
import org.example.search_advance.service.JwtService;
import org.example.search_advance.util.TokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.example.search_advance.util.TokenType.ACCESS_TOKEN;
import static org.example.search_advance.util.TokenType.REFRESH_TOKEN;

@Service
public class JwtServiceImp implements JwtService {
    @Value("${jwt.expiryHour}")
    private long expiryHour;

    @Value("${jwt.expiryDay}")
    private long expiryDay;

    @Value("${jwt.accessKey}")
    private String accessKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;

    @Override
    public String generateToken(UserDetails userDetail) {
        Long id = ((User) userDetail).getId();
        return generateToken(Map.of(
                "userId", id
//                "roles", userDetail.getAuthorities()
        ), userDetail);
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        return generateRefreshToken(new HashMap<>(), user);
    }

    @Override
    public String generateResetToken(UserDetails user) {
        return null;
    }

    @Override
    public String extractUsername(String token, TokenType type) {
        return extractClaim(token, type, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, TokenType type, UserDetails userDetails) {
        final String email = extractUsername(token, type);
        String email2 = ((User) userDetails).getEmail();
        return (email.equals(email2) && !isTokenExpired(token, type));
    }

    @Override
    public boolean validateToken(String token, TokenType type) {
        return false;
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        String email = ((User) userDetails).getEmail();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryHour))
                .signWith(getKey(ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        String email = ((User) userDetails).getEmail();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expiryDay))
                .signWith(getKey(REFRESH_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(TokenType type) {
        if (ACCESS_TOKEN.equals(type))
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
        else
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshKey));
    }

    private <T> T extractClaim(String token, TokenType type, Function<Claims, T> claimResolver) {
        final Claims claims = extraAllClaim(token, type);
        return claimResolver.apply(claims);
    }

    private Claims extraAllClaim(String token, TokenType type) {
        return Jwts.parser().setSigningKey(getKey(type)).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token, TokenType type) {
        return extractExpiration(token, type).before(new Date());
    }

    private Date extractExpiration(String token, TokenType type) {
        return extractClaim(token, type, Claims::getExpiration);
    }
}
