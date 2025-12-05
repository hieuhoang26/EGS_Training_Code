package org.example.search_advance.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import org.example.search_advance.model.User;
import org.example.search_advance.util.UserStatus;

public interface UserBasicInfo {

    String getFirstName();

    String getLastName();

    String getEmail();

    String getPhone();

    UserStatus getStatus();

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}



