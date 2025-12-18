package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserService  {
    UserDetailsService userDetailsService();

    Set<String> extractAuthorities(User user);

    User getById(UUID uuid);

    boolean existUser(String email);

    void save(User user);
}
