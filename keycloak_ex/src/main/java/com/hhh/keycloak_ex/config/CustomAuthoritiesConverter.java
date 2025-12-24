package com.hhh.keycloak_ex.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {

        Map<String, Object> accessClaims = source.getClaim("realm_access");

        if (accessClaims == null || !accessClaims.containsKey("roles")) {
            return Collections.emptyList();
        }
        Object roles = accessClaims.get("roles");

        if (!(roles instanceof List<?>)) {
            return Collections.emptyList();
        }
        return ((List<?>) roles).stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
