package org.example.search_advance.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.search_advance.dto.request.UserAvatarRequest;
import org.example.search_advance.dto.request.UserRequestDto;
import org.example.search_advance.dto.response.ResponseData;
import org.example.search_advance.dto.response.ResponseError;
import org.example.search_advance.dto.response.UserBasicInfo;
import org.example.search_advance.dto.response.UserDetailResponse;
import org.example.search_advance.exception.ResourceNotFoundException;
import org.example.search_advance.model.User;
import org.example.search_advance.service.AddressService;
import org.example.search_advance.service.UserService;
import org.example.search_advance.util.Gender;
import org.example.search_advance.util.UserStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
@Validated
@Slf4j

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AddressService addressService;

    @Operation(method = "POST", summary = "Add new user")
    @PostMapping
    public ResponseData<Long> addUser(@Valid @RequestBody UserRequestDto user) {
        log.info("Request add user, {} {}", user.getFirstName(), user.getLastName());

        try {
            long userId = userService.saveUser(user);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Add user Success", userId);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add user fail");
        }
    }
    @Operation(summary = "Update user", description = "Send a request via this API to update user")
    @PutMapping("/{userId}")
    public ResponseData<?> updateUser(@PathVariable @Min(1) long userId, @Valid @RequestBody UserRequestDto user) {
        log.info("Request update userId={}", userId);

        try {
            userService.updateUser(userId, user);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Updated");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Update user fail");
        }
    }

    @Operation(summary = "Change status of user", description = "Send a request via this API to change status of user")
    @PatchMapping("/{userId}")
    public ResponseData<?> updateStatus(@Min(1) @PathVariable int userId, @RequestParam UserStatus status) {
        log.info("Request change status, userId={}", userId);

        try {
            userService.changeStatus(userId, status);
            return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Changed");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Change status fail");
        }
    }

    @Operation(summary = "Delete user permanently", description = "Send a request via this API to delete user permanently")
    @DeleteMapping("/{userId}")
    public ResponseData<?> deleteUser(@PathVariable @Min(value = 1, message = "userId must be greater than 0") int userId) {
        log.info("Request delete userId={}", userId);

        try {
            userService.deleteUser(userId);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Deleted");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Delete user fail");
        }
    }

    @PostMapping(
            value = "/{userId}/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseData<String> uploadAvatar(
            @ModelAttribute UserAvatarRequest req
    ) {
        try {
            String avatarPath = userService.updateAvatar(req.getUserId(), req.getAvatar());
            return new ResponseData<>(HttpStatus.OK.value(), "Saved",avatarPath);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "fail");
        }

    }


    @Operation(summary = "Get user detail")
    @GetMapping("/{userId}")
    public ResponseData<UserDetailResponse> getUser(@PathVariable @Min(1) long userId) {
        log.info("Request get user detail, userId={}", userId);
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "user", userService.getUser(userId));
        } catch (ResourceNotFoundException e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }
    @Operation(summary = "Get basic info")
    @GetMapping("/info")
    public ResponseData<?> getBasicInfo() {
        try {
            List<?> allBasicInfo = addressService.getAddressInfo();
            return new ResponseData<>(HttpStatus.OK.value(), "user", allBasicInfo);
        } catch (ResourceNotFoundException e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }





    @Operation(summary = "Get list of users per pageNo")
    @GetMapping("/list")
    public ResponseData<?> getAllUsers(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                       @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
                                       @RequestParam(required = false) String sortBy) {
        log.info("Request get all of users");
        return new ResponseData<>(HttpStatus.OK.value(), "users", userService.getAllUsersWithSortBy(pageNo, pageSize, sortBy));
    }


    @Operation(summary = "Get list of users and search with paging and sorting by customize query")
    @GetMapping("/list-search")
    public ResponseData<?> getAllUsersAndSearchWithPagingAndSorting(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                                    @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                                    @RequestParam(required = false) String search,
                                                                    @RequestParam(required = false) String sortBy) {
        log.info("Request get list of users and search with paging and sorting");
        return new ResponseData<>(HttpStatus.OK.value(), "users", userService.getAllUsersAndSearchWithPagingAndSorting(pageNo, pageSize, search, sortBy));
    }
}
