package org.example.search_advance.service;


import org.example.search_advance.model.Permission;
import org.example.search_advance.model.Role;

import java.util.List;

public interface RoleService {
    Role getByName(String name);

    List<Permission> getPermissionsByRoleName(String roleName);
}
