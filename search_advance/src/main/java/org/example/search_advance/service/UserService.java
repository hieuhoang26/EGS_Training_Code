package org.example.search_advance.service;


import org.example.search_advance.dto.request.UserRequestDto;
import org.example.search_advance.dto.response.PageResponse;
import org.example.search_advance.dto.response.UserBasicInfo;
import org.example.search_advance.dto.response.UserDetailResponse;
import org.example.search_advance.util.UserStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {



    UserDetailResponse getUser(long userId);

    // Closed Projection
    List<UserBasicInfo> getAllBasicInfo();


    long saveUser(UserRequestDto request);

    void updateUser(long userId, UserRequestDto request);

    void changeStatus(long userId, UserStatus status);

    void deleteUser(long userId);

    public String updateAvatar(Long id, MultipartFile file);


    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy);

}
