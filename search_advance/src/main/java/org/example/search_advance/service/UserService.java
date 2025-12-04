package org.example.search_advance.service;


import org.example.search_advance.dto.response.PageResponse;
import org.example.search_advance.dto.response.UserBasicInfo;
import org.example.search_advance.dto.response.UserDetailResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDetailResponse getUser(long userId);

    List<UserBasicInfo> getAllBasicInfo();


    PageResponse<?> getAllUsersWithSortBy(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getAllUsersAndSearchWithPagingAndSorting(int pageNo, int pageSize, String search, String sortBy);

}
