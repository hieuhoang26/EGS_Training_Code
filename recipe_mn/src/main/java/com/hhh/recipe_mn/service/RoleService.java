package com.hhh.recipe_mn.service;

import com.hhh.recipe_mn.dto.request.RoleRequest;
import com.hhh.recipe_mn.model.Role;

import java.util.Set;
import java.util.UUID;

public interface RoleService {

    Role getByName(String name);

    Role create(RoleRequest req);

    Role assignPermissions(UUID roleId, Set<UUID> permissionIds);
}
