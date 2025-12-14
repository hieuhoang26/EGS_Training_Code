package org.example.search_advance.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.search_advance.service.UserService;
import org.example.search_advance.util.Uri;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SerConfig {
    private final UserService userService;
    private final PreFilter preFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService.userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(request -> request
//                .requestMatchers("/**").permitAll()
//                .anyRequest().permitAll());
//
//        http.sessionManagement(manager -> manager
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .csrf(AbstractHttpConfigurer::disable)
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
    /*
     Session
    */

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }


    @Bean
    public JsonAuthFilter jsonAuthFilter(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        JsonAuthFilter filter = new JsonAuthFilter(authenticationManager, securityContextRepository);
        filter.setSecurityContextRepository(securityContextRepository);
        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JsonAuthFilter jsonAuthFilter) throws Exception {

        http.securityContext(securityContext -> securityContext
                        .requireExplicitSave(false)
                        .securityContextRepository(securityContextRepository()))
                .logout(logout -> logout
                        .logoutUrl(Uri.LOGOUT)
                        .logoutSuccessHandler((req, res, auth) -> {
                    res.setStatus(HttpServletResponse.SC_OK);
                }))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterAt(jsonAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll())
                .authenticationProvider(authenticationProvider());
        return http.build();
    }


}

//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .usernameParameter("email") // Tên param username
//                        .passwordParameter("password") // Tên param password
//                        .defaultSuccessUrl("/", true)
//                        .failureUrl("/login?error=true")
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .invalidateHttpSession(true)    // Hủy session
//                        .deleteCookies("JSESSIONID")    // Xóa cookie
//                )