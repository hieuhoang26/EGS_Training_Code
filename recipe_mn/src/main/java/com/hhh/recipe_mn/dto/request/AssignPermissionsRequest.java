package com.hhh.recipe_mn.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class AssignPermissionsRequest {
    private Set<UUID> permissionIds;
}
