package org.example.search_advance.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserAvatarRequest {
    private Long userId;
    @Size(max = 5 * 1024 * 1024, message = "Avatar size must be less than 5MB")
    private MultipartFile avatar;
}
