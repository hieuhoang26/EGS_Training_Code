package com.hhh.recipe_mn.controller;

import com.hhh.recipe_mn.dto.request.PermissionRequest;
import com.hhh.recipe_mn.dto.response.ResponseData;
import com.hhh.recipe_mn.dto.response.ResponseError;
import com.hhh.recipe_mn.service.PermissionService;
import com.hhh.recipe_mn.utlis.Uri;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(Uri.PERMISSION)
@RequiredArgsConstructor
@Slf4j
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ResponseData<?> create(@RequestBody PermissionRequest req) {
        try {
            return new ResponseData<>(HttpStatus.CREATED.value(),
                    "Permission created successfully",
                    permissionService.create(req));
        } catch (Exception e) {
            log.error("Failed to create permission. errorMessage={}", e.getMessage(), e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Create permission failed");
        }
    }

    @GetMapping
    public ResponseData<?> getAll() {
        try {
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Get all permissions successfully",
                    permissionService.findAll());
        } catch (Exception e) {
            log.error("Failed to get all permissions. errorMessage={}", e.getMessage(), e);
            return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Get permissions failed");
        }
    }

    @PutMapping("/{id}")
    public ResponseData<?> update(@PathVariable UUID id,
                                  @RequestBody PermissionRequest req) {
        try {
            permissionService.update(id, req);
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Permission updated successfully");
        } catch (Exception e) {
            log.error("Failed to update permission with id={}. errorMessage={}", id, e.getMessage(), e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update permission failed");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> delete(@PathVariable UUID id) {
        try {
            permissionService.delete(id);
            return new ResponseData<>(HttpStatus.OK.value(),
                    "Permission deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete permission with id={}. errorMessage={}", id, e.getMessage(), e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete permission failed");
        }
    }
}
