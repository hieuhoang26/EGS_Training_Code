package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.dto.request.LogInRequest;
import com.hhh.recipe_mn.dto.request.SignUpRequest;
import com.hhh.recipe_mn.dto.response.ResponseData;
import com.hhh.recipe_mn.dto.response.TokenResponse;
import com.hhh.recipe_mn.exception.ResourceNotFoundException;
import com.hhh.recipe_mn.exception.UserAlreadyExistsException;
import com.hhh.recipe_mn.model.Role;
import com.hhh.recipe_mn.model.User;
import com.hhh.recipe_mn.repository.UserRepository;
import com.hhh.recipe_mn.security.JwtService;
import com.hhh.recipe_mn.service.AuthService;
import com.hhh.recipe_mn.service.RoleService;
import com.hhh.recipe_mn.service.UserService;
import com.hhh.recipe_mn.utlis.TokenType;
import com.hhh.recipe_mn.utlis.UserStatus;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.http.HttpHeaders.REFERER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final JwtService jwtService;
    final PasswordEncoder passwordEncoder;
    final RoleService roleService;
    final UserService userService;


    @Override
    public TokenResponse login(LogInRequest logInRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logInRequest.getEmail(), logInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Role
//        List<String> roles = user.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .filter(auth -> auth.startsWith("ROLE_"))
//                .toList();
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_"))
                .toList();

        List<String> permissions = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> !auth.startsWith("ROLE_"))
                .toList();


        // Permission
        return TokenResponse.builder()
                .id(String.valueOf(user.getId()))
                .username(user.getUsername())
                .roles(roles)
                .permissions(permissions)
                .phone(user.getPhone())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UUID signUp(SignUpRequest request) {
        if (userService.existUser(request.getEmail())) {
            throw new UserAlreadyExistsException("user");
        }

        String roleName = Optional.ofNullable(request.getRole())
                .orElse("ROLE_USER")
                .toUpperCase(Locale.ROOT);

        Role defaultRole = roleService.getByName(roleName);

        User user = User.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(
                        Optional.ofNullable(request.getPassword()).orElse("1234")
                ))
                .status(UserStatus.ACTIVE)
                .roles(Set.of(defaultRole))
                .build();

        userService.save(user);

        return user.getId();
    }

    @Override
    public TokenResponse refresh(HttpServletRequest request) {
        final String refreshToken = request.getHeader(REFERER);
        if (StringUtils.isBlank(refreshToken)) {
            throw new ResourceNotFoundException("Token must be not blank");
        }

        String email = jwtService.extractUsername(refreshToken, TokenType.REFRESH_TOKEN);
        log.info(email);
        UserDetails user = userService.userDetailsService().loadUserByUsername(email);
        if (jwtService.isValid(refreshToken, TokenType.REFRESH_TOKEN, user)) {
            String newToken = jwtService.generateToken(user);
            log.info(newToken);
            return TokenResponse.builder()
                    .tokenType("Bearer")
                    .accessToken(newToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            return null;
        }
    }
}
