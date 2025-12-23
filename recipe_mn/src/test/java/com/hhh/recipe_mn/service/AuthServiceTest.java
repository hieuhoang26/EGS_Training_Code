package com.hhh.recipe_mn.service;


import com.hhh.recipe_mn.dto.request.LogInRequest;
import com.hhh.recipe_mn.dto.request.SignUpRequest;
import com.hhh.recipe_mn.dto.response.TokenResponse;
import com.hhh.recipe_mn.exception.ResourceNotFoundException;
import com.hhh.recipe_mn.exception.UserAlreadyExistsException;
import com.hhh.recipe_mn.model.Role;
import com.hhh.recipe_mn.model.User;
import com.hhh.recipe_mn.repository.UserRepository;
import com.hhh.recipe_mn.security.JwtService;
import com.hhh.recipe_mn.service.imp.AuthServiceImpl;
import com.hhh.recipe_mn.utlis.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private UserDetailsService udsMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);

        udsMock = mock(UserDetailsService.class);
        when(userService.userDetailsService()).thenReturn(udsMock);


    }

    // =========================
    // LOGIN SUCCESS
    // =========================
    @Test
    void login_ShouldReturnTokenResponse() {
        LogInRequest loginRequest = new LogInRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        User mockUser = User.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .roles(Set.of())
                .build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(jwtService.generateToken(mockUser)).thenReturn("access-token");
        when(jwtService.generateRefreshToken(mockUser)).thenReturn("refresh-token");

        TokenResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
        assertEquals("test@example.com", response.getUsername());
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    // =========================
    // SIGNUP SUCCESS
    // =========================
    @Test
    void signUp_WhenUserNotExists_ShouldReturnUUID() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("newuser@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setRole("ROLE_USER");

        when(userService.existUser("newuser@example.com")).thenReturn(false);

        Role role = new Role();
        role.setName("ROLE_USER");
        when(roleService.getByName("ROLE_USER")).thenReturn(role);

        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(UUID.randomUUID());
            return null;
        }).when(userService).save(any(User.class));

        UUID userId = authService.signUp(signUpRequest);

        assertNotNull(userId);
        verify(userService, times(1)).existUser("newuser@example.com");
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void signUp_WhenUserAlreadyExists_ShouldThrowException() {
        SignUpRequest request = new SignUpRequest();
        request.setEmail("existing@example.com");

        when(userService.existUser("existing@example.com")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> authService.signUp(request));
        verify(userService, times(1)).existUser("existing@example.com");
        verify(userService, never()).save(any(User.class));
    }

    // =========================
    // REFRESH SUCCESS
    // =========================
    @Test
    void refresh_WithValidToken_ShouldReturnTokenResponse() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(anyString())).thenReturn("refresh-token");

        UserDetails userDetails = mock(UserDetails.class);
        when(jwtService.extractUsername("refresh-token", TokenType.REFRESH_TOKEN)).thenReturn("user@example.com");
        when(udsMock.loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(jwtService.isValid("refresh-token", TokenType.REFRESH_TOKEN, userDetails)).thenReturn(true);
        when(jwtService.generateToken(userDetails)).thenReturn("new-access-token");

        TokenResponse response = authService.refresh(request);

        assertNotNull(response);
        assertEquals("new-access-token", response.getAccessToken());
        assertEquals("refresh-token", response.getRefreshToken());
    }

    @Test
    void refresh_WithBlankToken_ShouldThrowException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(anyString())).thenReturn("   "); // blank

        assertThrows(ResourceNotFoundException.class, () -> authService.refresh(request));
    }

    @Test
    void refresh_WithInvalidToken_ShouldReturnNull() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(anyString())).thenReturn("refresh-token");

        UserDetails userDetails = mock(UserDetails.class);
        when(jwtService.extractUsername("refresh-token", TokenType.REFRESH_TOKEN)).thenReturn("user@example.com");
        when(userService.userDetailsService().loadUserByUsername("user@example.com")).thenReturn(userDetails);
        when(jwtService.isValid("refresh-token", TokenType.REFRESH_TOKEN, userDetails)).thenReturn(false);

        TokenResponse response = authService.refresh(request);
        assertNull(response);
    }
}
