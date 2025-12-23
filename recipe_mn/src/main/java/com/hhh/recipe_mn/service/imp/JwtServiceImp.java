package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.model.User;
import com.hhh.recipe_mn.security.JwtService;
import com.hhh.recipe_mn.utlis.JwtKeyLoader;
import com.hhh.recipe_mn.utlis.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static com.hhh.recipe_mn.utlis.TokenType.ACCESS_TOKEN;
import static com.hhh.recipe_mn.utlis.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class JwtServiceImp implements JwtService {
    /*
    Best practice:
        private key: sign, verify
        public key: verify
    Flow:
        [Server] --(sign =  private key)--> JWT --> [Client]
        [Client] --(send JWT)--> [Server]
        [Server] --(verify by public key)--> payload
     SignatureAlgorithm:
        - Symmetric:  HS256 - Secret Key
        - Asymmetric : RS256 - Private Key & Public Key
     */


    private final JwtKeyLoader keyLoader;

    @Value("${jwt.expiryHour}")
    private long expiryHour;

    @Value("${jwt.expiryDay}")
    private long expiryDay;

    @Value("${jwt.publicKeyAccess}")
    private String publicKeyAccess;

    @Value("${jwt.privateKeyAccess}")
    private String privateKeyAccess;

    @Value("${jwt.publicKeyRefresh}")
    private String publicKeyRefresh;


    @Value("${jwt.privateKeyRefresh}")
    private String privateKeyRefresh;

    @Override
    public String generateToken(UserDetails userDetail) {
        UUID id = ((User) userDetail).getId();
        return generateToken(Map.of(
                "userId", id,
                "type", "access_token"
//                "roles", userDetail.getAuthorities()
        ), userDetail);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetail) {
        UUID id = ((User) userDetail).getId();
        return generateRefreshToken(Map.of(
                "userId", id,
                "type", "refresh_token"
        ), userDetail);
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
        Claims claims = extraAllClaim(token, type);
        return !isTokenExpired(token, type)
                && type.name().toLowerCase().equals(claims.get("type"));
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        String email = ((User) userDetails).getEmail();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * expiryHour))
                .signWith(getPrivateKey(ACCESS_TOKEN), SignatureAlgorithm.RS256)
                .compact();
    }

    private String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        String email = ((User) userDetails).getEmail();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * expiryDay))
                .signWith(getPrivateKey(REFRESH_TOKEN), SignatureAlgorithm.RS256)
                .compact();
    }

    private PrivateKey getPrivateKey(TokenType type) {

        String path = (type == ACCESS_TOKEN ? privateKeyAccess : privateKeyRefresh);
        return keyLoader.loadPrivateKey(path);
    }

    private PublicKey getPublicKey(TokenType type) {

        String path = (type == REFRESH_TOKEN ? publicKeyRefresh : publicKeyAccess);
        return keyLoader.loadPublicKey(path);
    }


    private <T> T extractClaim(String token, TokenType type, Function<Claims, T> claimResolver) {
        final Claims claims = extraAllClaim(token, type);
        return claimResolver.apply(claims);
    }

    private Claims extraAllClaim(String token, TokenType type) {
        return Jwts.parser().setSigningKey(getPublicKey(type)).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token, TokenType type) {
        return extractExpiration(token, type).before(new Date());
    }

    private Date extractExpiration(String token, TokenType type) {
        return extractClaim(token, type, Claims::getExpiration);
    }
}
