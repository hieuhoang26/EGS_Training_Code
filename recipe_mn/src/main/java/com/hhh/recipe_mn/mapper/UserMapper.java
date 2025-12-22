package com.hhh.recipe_mn.mapper;

import com.hhh.recipe_mn.dto.response.UserResponse;
import com.hhh.recipe_mn.model.Permission;
import com.hhh.recipe_mn.model.Role;
import com.hhh.recipe_mn.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    @Mapping(target = "roles", expression = "java(mapRoles(user))")
//    @Mapping(target = "permissions", expression = "java(mapPermissions(user))")
    UserResponse toResponse(User user);


    default Set<String> mapRoles(User user) {
        if (user.getRoles() == null) return Set.of();

        return user.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    default Set<String> mapPermissions(User user) {
        if (user.getRoles() == null) return Set.of();

        return user.getRoles()
                .stream()
                .flatMap(r -> r.getPermissions().stream())
                .map(Permission::getName)
                .collect(Collectors.toSet());
    }
}
