package com.hhh.recipe_mn.controller;

import com.hhh.recipe_mn.dto.request.AssignRolesRequest;
import com.hhh.recipe_mn.dto.request.UserRequest;
import com.hhh.recipe_mn.dto.response.ResponseData;
import com.hhh.recipe_mn.dto.response.ResponseError;
import com.hhh.recipe_mn.dto.response.UserResponse;
import com.hhh.recipe_mn.service.UserService;
import com.hhh.recipe_mn.utlis.Uri;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Uri.USER)
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/roles")
    public ResponseData<?> assignRoles(@PathVariable UUID id, @RequestBody AssignRolesRequest req) {
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Roles assigned successfully", userService.assignRoles(id, req.getRoleIds()));
        } catch (Exception e) {
            log.error("Failed to assign roles for user id={}. errorMessage={}", id, e.getMessage(), e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Assign roles failed");
        }
    }

    @PostMapping
    @Operation(summary = "Create user")
    public ResponseData<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        try {
            UserResponse rs = userService.create(request);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Create user successfully", rs);
        } catch (Exception e) {
            log.error("create user failed", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public ResponseData<UserResponse> getUserById(@PathVariable UUID id) {
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Success", userService.getDetailById(id));
        } catch (Exception e) {
            log.error("get user failed", e);
            return new ResponseError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all users")
    public ResponseData<List<UserResponse>> getAllUsers() {
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Success", userService.getAll());
        } catch (Exception e) {
            log.error("get all users failed", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    public ResponseData<Void> updateUser(@PathVariable UUID id, @RequestBody @Valid UserRequest request) {
        try {
            userService.update(id, request);
            return new ResponseData<>(HttpStatus.OK.value(), "Update user successfully");
        } catch (Exception e) {
            log.error("update user failed", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    public ResponseData<Void> deleteUser(@PathVariable UUID id) {
        try {
            userService.delete(id);
            return new ResponseData<>(HttpStatus.OK.value(), "Delete user successfully");
        } catch (Exception e) {
            log.error("delete user failed", e);
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }


}
