package com.hhh.recipe_mn.service.imp;

import com.hhh.recipe_mn.dto.request.PermissionRequest;
import com.hhh.recipe_mn.exception.ResourceNotFoundException;
import com.hhh.recipe_mn.model.Permission;
import com.hhh.recipe_mn.repository.PermissionRepository;
import com.hhh.recipe_mn.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionServiceImp implements PermissionService {
    private final PermissionRepository permissionRepo;

    @Override
    public Permission create(PermissionRequest req) {
        if (permissionRepo.existsByName(req.getName())) {
            throw new IllegalArgumentException("Permission already exists");
        }
        Permission p = new Permission();
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        return permissionRepo.save(p);
    }

    public List<Permission> findAll() {
        return permissionRepo.findAll();
    }

    @Override
    public Permission update(UUID id, PermissionRequest req) {
        Permission p = permissionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found"));
        p.setDescription(req.getDescription());
        return permissionRepo.save(p);
    }

    @Override
    public void delete(UUID id) {
        permissionRepo.deleteById(id);
    }
}
