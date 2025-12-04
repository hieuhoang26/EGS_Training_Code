package org.example.search_advance.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import org.example.search_advance.model.User;
import org.example.search_advance.util.UserStatus;

public record UserBasicInfo(
        String firstName,
        String lastName,
        String email,
        String phone,
        UserStatus status
) {}
