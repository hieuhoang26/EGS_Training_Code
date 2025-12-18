package com.hhh.demo.service;

import com.hhh.demo.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder encoder;
    private final UserDetailsService userDetailsService;

    public TokenResponse generateTokens(Authentication auth) {
        Instant now = Instant.now();

        String accessToken = generateAccessToken(auth, now);
        String refreshToken = UUID.randomUUID().toString();



        return new TokenResponse(accessToken, refreshToken);
    }

//    public TokenResponse refresh(String refreshToken) {
//        RefreshToken token = refreshTokenRepository
//                .findValidToken(refreshToken)
//                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
//
//        UserDetails user = userDetailsService
//                .loadUserByUsername(token.getUsername());
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(
//                user.getUsername(),
//                null,
//                user.getAuthorities()
//        );
//
//        String newAccessToken =
//                generateAccessToken(auth, Instant.now());
//
//        return new TokenResponse(newAccessToken, refreshToken);
//    }

    private String generateAccessToken(Authentication auth, Instant now) {
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .subject(auth.getName())
                .claim("scope", scope)
                .claim("type", "access")
                .build();

        return encoder.encode(
                JwtEncoderParameters.from(claims)
        ).getTokenValue();
    }
}
