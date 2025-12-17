//package com.hhh.recipe_mn.service.imp;
//
//import com.hhh.recipe_mn.model.User;
//import com.hhh.recipe_mn.security.JwtService;
//import com.hhh.recipe_mn.utlis.TokenType;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.jwt.*;
//import org.springframework.stereotype.Service;
//
//import java.time.Instant;
//import java.util.Date;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class JwtServiceImp implements JwtService {
//    @Value("${jwt.expiryHour}")
//    private long expHour;
//
//    @Value("${jwt.expiryDay}")
//    private long expDay;
//
//    final JwtEncoder jwtEncoder;
//    final JwtDecoder jwtDecoder;
//
//
//    @Override
//    public String generateAccessToken(Authentication authentication, UserDetails user) {
//        Instant now = Instant.now();
//
//        String scope = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
//
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer("self")
//                .issuedAt(now)
//                .expiresAt(now.plusSeconds(expHour))
//                .subject(authentication.getName())
//                .claim("authorities", scope)
//                .claim("token_type", "access")
//                .build();
//
//        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    }
//
//    @Override
//    public String generateRefreshToken(Authentication authentication, UserDetails user) {
//        Instant now = Instant.now();
//
//        String scope = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
//
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer("self")
//                .issuedAt(now)
//                .expiresAt(now.plusSeconds(expDay*7))
//                .subject(authentication.getName())
//                .claim("authorities", scope)
//                .claim("token_type", "refresh")
//                .build();
//        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//
//    }
//
//    @Override
//    public boolean isValid(String token, UserDetails user) {
//        final String email =
//        return false;
//    }
//
//    @Override
//    public String extractUsername(String token) {
//        return extractClaim(token, claims -> (String) claims.get("sub"));
//    }
//
////    @Override
////    public boolean validateToken(String token) {
////        return false;
////    }
////
////    @Override
////    public String extractUsername(String token) {
////        return null;
////    }
//
//    private Map<String, Object> extractAllClaims(String token) {
//        Jwt jwt = jwtDecoder.decode(token);
//        return jwt.getClaims();
//    }
//    private <T> T extractClaim(String token, Function<Map<String, Object>, T> claimResolver){
//        final Map<String, Object> claims = extractAllClaims(token);
//        return claimResolver.apply(claims);
//    }
//
//    private Instant extractExpiration(String token) {
//        return extractClaim(token, claims -> (Instant) claims.get("exp"));
//    }
//
//    private boolean isExpired(String token){
//        return extractExpiration(token).isBefore(Instant.now());
//    }
//}
