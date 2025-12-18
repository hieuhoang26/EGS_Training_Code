package com.hhh.recipe_mn.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class ImageRequest {
    private UUID id;

    @NotBlank
    private String url;
    private Boolean isPrimary;
}