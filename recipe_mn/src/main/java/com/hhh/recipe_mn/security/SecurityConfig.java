package com.hhh.recipe_mn.security;

import com.hhh.recipe_mn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

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

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                // Auth
                .requestMatchers("/api/v1/auth/**").permitAll()

                // USER
                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("USER:READ")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAuthority("USER:UPDATE")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("USER:DELETE")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**/roles").hasAuthority("USER:ROLE_UPDATE")
                .requestMatchers(HttpMethod.POST, "/api/v1/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/all").hasRole("ADMIN")

                // ROLE
                .requestMatchers(HttpMethod.PUT, "/api/v1/roles/**/permissions").hasAuthority("ROLE:PERMISSION_UPDATE")
                .requestMatchers(HttpMethod.POST, "/api/v1/roles").hasAuthority("ROLE:CREATE")

                // PERMISSION
                .requestMatchers(HttpMethod.POST, "/api/v1/permissions/**").hasRole("ADMIN")

                // RECIPE
                .requestMatchers(HttpMethod.POST, "/api/v1/recipe/**").hasAuthority("RECIPE:CREATE")
                .requestMatchers(HttpMethod.PUT, "/api/v1/recipe/**").hasAuthority("RECIPE:UPDATE")
                .requestMatchers(HttpMethod.POST, "/api/v1/recipe/search").hasAuthority("RECIPE:READ")

                // SHOPPING LIST
                .requestMatchers(HttpMethod.POST, "/api/v1/shopping/generate/**").hasAuthority("SHOPPING:CREATE")
                .requestMatchers(HttpMethod.GET, "/api/v1/shopping/**").hasAuthority("SHOPPING:READ")

                .anyRequest().authenticated())
        ;

        http.sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer ignoreResources() {
        return (webSecurity) -> webSecurity
                .ignoring()
                .requestMatchers("/actuator/**", "/v3/**", "/webjars/**", "/swagger-ui*/*swagger-initializer.js",
                        "/swagger-ui*/**");
    }

//    @Bean
//    @Order(2)
//    public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .csrf(Customizer.withDefaults())
//                .authenticationProvider(authenticationProvider())
////                .formLogin(form -> form
////                        .loginPage("/login")
////                        .loginProcessingUrl("/doLogin")
////                        .defaultSuccessUrl("/", true)
////                        .usernameParameter("email")
////                        .passwordParameter("password")
////                        .failureUrl("/login?error=true")
////                        .permitAll()
////                )
//                .formLogin(Customizer.withDefaults())
//
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID")
//                )
//                .sessionManagement(sm ->
//                        sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                                .maximumSessions(1)   // 1 user = 1 session
//                                .maxSessionsPreventsLogin(false)
//                );
//
//        return http.build();
//        // save session to DB --> spring-session-jdbc --> auto gen table
//
//    }


}
