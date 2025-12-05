package org.example.search_advance.repository;


import org.example.search_advance.dto.response.AddressValue;
import org.example.search_advance.dto.response.UserBasicInfo;

import org.example.search_advance.model.User;
import org.example.search_advance.util.Gender;
import org.example.search_advance.util.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<UserBasicInfo> getBasicInfoByStatus(UserStatus status);

    List<AddressValue> findAllProjectedBy();

}
