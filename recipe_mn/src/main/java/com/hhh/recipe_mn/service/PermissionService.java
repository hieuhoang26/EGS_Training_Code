package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.dto.request.PermissionRequest;
import com.hhh.recipe_mn.model.Permission;

import java.util.List;
import java.util.UUID;

public interface PermissionService {
    Permission create(PermissionRequest req);

    List<Permission> findAll();

    Permission update(UUID id, PermissionRequest req);

    void delete(UUID id);

}
