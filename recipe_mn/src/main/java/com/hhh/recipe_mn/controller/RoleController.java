package com.hhh.recipe_mn.controller;

import com.hhh.recipe_mn.dto.request.AssignPermissionsRequest;
import com.hhh.recipe_mn.dto.request.RoleRequest;
import com.hhh.recipe_mn.dto.response.ResponseData;
import com.hhh.recipe_mn.dto.response.ResponseError;
import com.hhh.recipe_mn.service.RoleService;
import com.hhh.recipe_mn.utlis.Uri;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(Uri.ROLE)
@RequiredArgsConstructor
@Slf4j
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseData<?> create(@RequestBody RoleRequest req) {
        try {
            return new ResponseData<>(HttpStatus.CREATED.value(),
                    "Role created successfully",
                    roleService.create(req));
        } catch (Exception e) {
            log.error("Failed to create role. errorMessage={}", e.getMessage(), e);
            return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Create role failed");
        }
    }

    @PutMapping("/{id}/permissions")
    public ResponseData<?> assignPermissions(
            @PathVariable UUID id,
            @RequestBody AssignPermissionsRequest req) {
        try {
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Permissions assigned successfully",
                    roleService.assignPermissions(id, req.getPermissionIds()));
        } catch (Exception e) {
            log.error("Failed to assign permissions for role id={}. errorMessage={}",
                    id, e.getMessage(), e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Assign permissions failed");
        }
    }
}
