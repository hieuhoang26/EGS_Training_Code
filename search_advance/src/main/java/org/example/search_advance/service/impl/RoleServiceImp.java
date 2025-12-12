package org.example.search_advance.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.search_advance.model.Permission;
import org.example.search_advance.model.Role;
import org.example.search_advance.model.RoleHasPermission;
import org.example.search_advance.repository.RoleRepository;
import org.example.search_advance.service.RoleService;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    final RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name).orElse(null) ;
    }

    @Override
    public List<Permission> getPermissionsByRoleName(String roleName) {
        return roleRepository.findWithPermissionsByName(roleName)
                .map(role -> role.getRoles().stream()
                        .map(RoleHasPermission::getPermission)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}
