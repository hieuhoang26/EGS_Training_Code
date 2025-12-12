package org.example.search_advance.service.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.search_advance.dto.request.LogInRequest;
import org.example.search_advance.dto.request.SignUpRequest;
import org.example.search_advance.dto.response.ResponseData;
import org.example.search_advance.dto.response.TokenResponse;
import org.example.search_advance.exception.ResourceNotFoundException;
import org.example.search_advance.exception.UserAlreadyExistsException;
import org.example.search_advance.model.Permission;
import org.example.search_advance.model.Role;
import org.example.search_advance.model.User;
import org.example.search_advance.model.UserHasRole;
import org.example.search_advance.service.*;
import org.example.search_advance.util.TokenType;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.stream.LangCollectors.collect;
import static org.springframework.http.HttpHeaders.REFERER;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    final AuthenticationManager authenticationManager;
    final JwtService jwtService;
    final UserService userService;
    final RoleService roleService;
    final PasswordEncoder passwordEncoder;
    final UserHasRoleService userHasRoleService;

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
                .map(role -> role.substring("ROLE_".length()))
                .map(String::toLowerCase)
                .toList();

        // Permission
        List<String> permissions = roles.stream()
                .map(roleService::getPermissionsByRoleName)
                .flatMap(List::stream)
                .map(Permission::getName)
                .distinct()
                .toList();
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
    public Long signUp(SignUpRequest request) {
        if (userService.existUser(request.getEmail())) {
            throw new UserAlreadyExistsException("user");
        }

        User user = User.builder()
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword() != null ? request.getPassword() : "1234"))
                .username(request.getUsername())
                .build();
        userService.save(user);
        Role defaultRole = roleService.getByName(request.getRole().toLowerCase(Locale.ROOT));
        UserHasRole userHasRole = new UserHasRole();
        userHasRole.setUser(user);
        userHasRole.setRole(defaultRole);
        log.info(user.toString() + defaultRole.toString());
        userHasRoleService.save(userHasRole);

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
