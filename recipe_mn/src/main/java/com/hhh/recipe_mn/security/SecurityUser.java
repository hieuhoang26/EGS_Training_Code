package com.hhh.recipe_mn.security;

import com.hhh.recipe_mn.model.Permission;
import com.hhh.recipe_mn.model.Role;
import com.hhh.recipe_mn.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class SecurityUser implements UserDetails {

    private final User user;
    private Collection<String> permissions;

    public SecurityUser(User user) {
        this.user = user;
        this.permissions = user.getRoles().stream()
                // 1. Dùng flatMap để "làm phẳng" Set<Set<Permission>> thành Stream<Permission>
                .flatMap(role -> role.getPermissions().stream())
                // 2. Lấy tên (name) của từng Permission
                .map(Permission::getName)
                // 3. Thu thp kết quả vào một Set (để loại bỏ trùng lặp)
                .collect(Collectors.toSet());
        // ------------------------------
    }


    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roleList = user.getRoles().stream().map(Role::getName).toList();
        return roleList.stream().map(s -> new SimpleGrantedAuthority(s.toUpperCase())).toList();

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
