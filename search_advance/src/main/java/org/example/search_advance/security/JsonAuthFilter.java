package org.example.search_advance.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.search_advance.dto.request.LogInRequest;
import org.example.search_advance.util.Uri;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JsonAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final SecurityContextRepository securityContextRepository;



    public JsonAuthFilter(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
        this.setAuthenticationManager(authenticationManager);
        this.setFilterProcessesUrl(Uri.LOGIN);
    }



    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response) {

        if (!request.getMethod().equals("POST")) {
            throw new RuntimeException("POST method required");
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            LogInRequest login = mapper.readValue(request.getInputStream(), LogInRequest.class);

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);

        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(context, request, response);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setMaxInactiveInterval(1800);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("success", true);
        responseBody.put("message", "Login successful");
        responseBody.put("user", authResult.getName());
        responseBody.put("sessionId", session != null ? session.getId() : null);

        new ObjectMapper().writeValue(response.getWriter(), responseBody);
    }
}

