package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.dto.request.RoleRequest;
import com.hhh.recipe_mn.exception.ResourceNotFoundException;
import com.hhh.recipe_mn.model.Permission;
import com.hhh.recipe_mn.model.Role;
import com.hhh.recipe_mn.repository.PermissionRepository;
import com.hhh.recipe_mn.repository.RoleRepository;
import com.hhh.recipe_mn.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public Role create(RoleRequest req) {
        if (roleRepository.existsByName(req.getName())) {
            throw new IllegalArgumentException("Role already exists");
        }
        Role role = new Role();
        role.setName(req.getName());
        role.setDescription(req.getDescription());
        return roleRepository.save(role);
    }

    @Override
    public Role assignPermissions(UUID roleId, Set<UUID> permissionIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        Set<Permission> permissions =
                new HashSet<>(permissionRepository.findAllById(permissionIds));

        role.setPermissions(permissions);
        return roleRepository.save(role);
    }
}
