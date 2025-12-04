package org.example.search_advance.repository;


import org.example.search_advance.dto.response.UserBasicInfo;
import org.example.search_advance.model.User;
import org.example.search_advance.util.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
            select new org.example.search_advance.dto.response.UserBasicInfo(
                u.firstName,
                u.lastName,
                u.email,
                u.phone,
                u.status
            )
            from User u
            where u.status = :status
           """)
    List<UserBasicInfo> getBasicInfoByStatus(UserStatus status);

}
