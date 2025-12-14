package org.example.search_advance.repository;


import org.example.search_advance.dto.response.UserBasicInfo;

import org.example.search_advance.model.User;
import org.example.search_advance.util.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


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
        where u.status = :statusFilter
    """)
    List<UserBasicInfo> getBasicInfoByStatus(@Param("statusFilter") UserStatus status);


    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
     Optional<User>  findByEmail(@Param("email") String email);

     boolean existsByEmail(String email);
}
