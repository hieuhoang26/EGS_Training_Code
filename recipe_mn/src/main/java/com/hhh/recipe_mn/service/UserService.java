package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.dto.request.UserRequest;
import com.hhh.recipe_mn.dto.response.UserResponse;
import com.hhh.recipe_mn.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService  {
    UserDetailsService userDetailsService();


    User getById(UUID uuid);

    boolean existUser(String email);

    void save(User user);

    User assignRoles(UUID userId, Set<UUID> roleIds);

    UserResponse create(UserRequest request);

    UserResponse getDetailById(UUID id);

    List<UserResponse> getAll();

    UserResponse update(UUID id, UserRequest request);

    void delete(UUID id);
}
